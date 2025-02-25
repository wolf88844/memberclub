/**
 * @(#)AbstractRabbitmqConsumerConfiguration.java, 一月 22, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.mq.rabbitmq;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.mq.ConsumeStatauEnum;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import com.memberclub.infrastructure.mq.RabbitRegisterConfiguration;
import com.memberclub.sdk.common.SwitchEnum;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * AbstractRabbitmqConsumerConfiguration 是一个抽象类，用于配置和管理 RabbitMQ 消费者。
 * 它负责初始化消费者映射，并提供消息消费和重试逻辑。
 *
 * @author 掘金五阳
 */
public class AbstractRabbitmqConsumerConfiguration {

    /**
     * 日志记录器，用于记录日志信息。
     */
    public static final Logger LOG = LoggerFactory.getLogger(RabbitmqConsumerConfiguration.class);

    /**
     * 重试计数的头字段名称。
     */
    public static final String RETRY_COUNT = "retry-count";

    /**
     * 存储队列名称到消费者列表的映射。
     */
    private final Map<String, List<MessageQueueConsumerFacade>> consumerMap = Maps.newHashMap();

    /**
     * 初始化方法，在Spring容器启动后自动调用。
     * 该方法从Spring上下文中获取所有实现了 {@link MessageQueueConsumerFacade} 的Bean，
     * 并根据每个消费者的注册信息将其添加到相应的队列中。
     */
    @PostConstruct
    public void init() {
        try {
            // 获取所有实现了MessageQueueConsumerFacade接口的Bean
            Map<String, MessageQueueConsumerFacade> consumers =
                    ApplicationContextUtils.getContext().getBeansOfType(MessageQueueConsumerFacade.class);

            if (MapUtils.isNotEmpty(consumers)) {
                for (Map.Entry<String, MessageQueueConsumerFacade> entry : consumers.entrySet()) {
                    MQQueueEnum mqQueueEnum = entry.getValue().register();
                    // 确保队列名称对应的消费者列表存在
                    consumerMap.putIfAbsent(mqQueueEnum.getQueneName(), Lists.newArrayList());
                    // 将消费者添加到对应队列的消费者列表中
                    consumerMap.get(mqQueueEnum.getQueneName()).add(entry.getValue());
                }
            }
        } catch (Exception e) {
            LOG.error("初始化RabbitMQ消费者配置失败", e);
        }
    }

    /**
     * 消费消息并处理失败重试的逻辑。
     * 如果消费成功，则确认消息；如果消费失败且未达到最大重试次数，则将消息重新投递到延迟队列；
     * 如果达到最大重试次数，则记录警告日志并确认消息。
     *
     * @param value 消息内容
     * @param channel RabbitMQ通道
     * @param message 消息对象
     * @param retryConfig 重试配置
     * @param queue 队列枚举
     * @throws IOException 如果发生I/O错误
     */
    protected void consumeAndFailRetry(String value, Channel channel, Message message,
                                       SwitchEnum retryConfig,
                                       MQQueueEnum queue) throws IOException {
        LOG.info("收到rabbitmq消息 queue:{}, message:{}", queue.getDelayQueneName(), value);

        boolean fail = false;
        // 遍历当前队列的所有消费者，尝试消费消息
        for (MessageQueueConsumerFacade messageQueueConsumerFacade : consumerMap.get(queue.getQueneName())) {
            try {
                ConsumeStatauEnum status = messageQueueConsumerFacade.consume(value);
                if (status == ConsumeStatauEnum.retry) {
                    fail = true;
                }
            } catch (Exception e) {
                LOG.error("消费消息失败", e);
                fail = true;
            }
        }

        if (!fail) {
            // 如果所有消费者都成功消费消息，则确认消息
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            return;
        }

        // 获取消息的重试计数
        Integer retryCount;
        Map<String, Object> headers = message.getMessageProperties().getHeaders();
        if (!headers.containsKey(RETRY_COUNT)) {
            retryCount = 0;
        } else {
            retryCount = (Integer) headers.get(RETRY_COUNT);
        }

        // 判断是否满足最大重试次数
        int maxRetryTimes = retryConfig.getInt();
        if (retryCount++ < maxRetryTimes) {
            // 更新重试计数并重新发送到延迟队列
            headers.put("retry-count", retryCount);
            AMQP.BasicProperties basicProperties = new AMQP.BasicProperties()
                    .builder().contentType("text/plain").headers(headers).build();
            channel.basicPublish(RabbitRegisterConfiguration.DEAD_LETTER_EXCHANGE,
                    queue.getDelayQueneName(), basicProperties,
                    message.getBody());
            LOG.warn("消费失败,将消息延迟 {} 毫秒后,重新投递到队列:{}, 当前:retryCount:{}, maxRetryCount:{}, message:{}",
                    queue.getDelayMillSeconds(),
                    queue.getQueneName(),
                    (retryCount - 1),
                    maxRetryTimes, value);
        } else {
            // 达到最大重试次数，记录警告日志并确认消息
            LOG.warn("消费失败,达到最大重试次数,无法重新投递到队列:{}, 当前:retryCount:{}, maxRetryCount:{}, message:{}",
                    queue.getQueneName(),
                    (retryCount - 1),
                    maxRetryTimes, value);
        }

        // 确认消息，无论是否重试或达到最大重试次数
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
