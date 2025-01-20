/**
 * @(#)RabbitmqConsumerFacade.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.consumer.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.mq.ConsumeStatauEnum;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import com.memberclub.infrastructure.mq.RabbitRegisterConfiguration;
import com.memberclub.infrastructure.mq.producer.impl.RabbitmqPublishFacadeImpl;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.memberclub.infrastructure.mq.MQContants.TRADE_EVENT_FOR_PRE_FINANCE;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "rabbitmq", matchIfMissing = true)
@Configuration
public class RabbitmqConsumerConfiguration {

    public static final Logger LOG = LoggerFactory.getLogger(RabbitmqConsumerConfiguration.class);


    @Autowired
    private RabbitmqPublishFacadeImpl rabbitmqPublishFacade;

    private Map<String, List<MessageQueueConsumerFacade>> consumerMap = Maps.newHashMap();

    @Autowired
    private CommonRabbitmqRetryableQueue commonRabbitmqRetryableQueue;

    @PostConstruct
    public void init() {
        Map<String, MessageQueueConsumerFacade> consumers = null;
        try {
            consumers =
                    ApplicationContextUtils.getContext().getBeansOfType(MessageQueueConsumerFacade.class);
        } catch (Exception e) {
        }
        if (MapUtils.isNotEmpty(consumers)) {
            for (Map.Entry<String, MessageQueueConsumerFacade> entry : consumers.entrySet()) {
                MQQueueEnum mqQueueEnum = entry.getValue().register();
                consumerMap.putIfAbsent(mqQueueEnum.getQueneName(), Lists.newArrayList());
                consumerMap.get(mqQueueEnum.getQueneName()).add(entry.getValue());
            }
        }
    }


    @RabbitListener(queues = {TRADE_EVENT_FOR_PRE_FINANCE})
    @RabbitHandler
    public void consumeTradeEventPreFinanceQueue(String value, Channel channel, Message message) throws IOException {
        doConsume(value, channel, message, MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE);
    }

    public void doConsume(String value, Channel channel, Message message, MQQueueEnum queue) throws IOException {
        LOG.info("rabbitmq 收到消息:{}", value);

        boolean fail = false;
        for (MessageQueueConsumerFacade messageQueueConsumerFacade : consumerMap.get(queue.getQueneName())) {
            try {
                ConsumeStatauEnum status = messageQueueConsumerFacade.consume(value);
                if (status == ConsumeStatauEnum.retry) {
                    fail = true;
                }
            } catch (Exception e) {
                fail = true;
            }
        }
        if (!fail) {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } else {
            Integer retryCount;
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            if (!headers.containsKey("retry-count")) {
                retryCount = 0;
            } else {
                retryCount = (Integer) headers.get("retry-count");
            }
            //判断是否满足最大重试次数(重试3次)
            if (retryCount++ < 3) {
                headers.put("retry-count", retryCount);
                //重新发送到MQ中
                AMQP.BasicProperties basicProperties = new AMQP.BasicProperties().builder().contentType("text/plain").headers(headers).build();
                channel.basicPublish(RabbitRegisterConfiguration.DEAD_LETTER_EXCHANGE,
                        MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getDelayQueneName(), basicProperties,
                        message.getBody());
            } else {
                LOG.error("达到最大重试次数 retryCount:{}, message:{}", (retryCount - 1), value);
            }

            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        }
    }

}