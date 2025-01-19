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
import com.memberclub.infrastructure.mq.producer.impl.RabbitmqPublishFacadeImpl;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
public class RabbitmqConsumerConfiguration implements ApplicationRunner {

    public static final Logger LOG = LoggerFactory.getLogger(RabbitmqConsumerConfiguration.class);


    @Autowired
    private RabbitmqPublishFacadeImpl rabbitmqPublishFacade;

    @Autowired
    private RabbitmqDeadLetterConfiguration rabbitmqDeadLetterConfiguration;

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
        commonRabbitmqRetryableQueue.registerRetryableQueue(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {

    }
    /* @Bean
    public Queue tradeEventPreFinanceQueue() {
        return QueueBuilder.durable(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName())
                .build();
    }

    @Bean
    public org.springframework.amqp.core.Binding tradeEventForFinanceQueueBinding() {
        return BindingBuilder.bind(tradeEventPreFinanceQueue())
                .to(rabbitmqDeadLetterConfiguration.deadLetterExchange())
                .with(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName());
    }


    @Bean
    public Queue tradeEventPreFinanceDelayQueue() {
        return QueueBuilder.durable(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getDelayQueneName())
                .deadLetterExchange(RabbitmqDeadLetterConfiguration.DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName())
                .ttl(1000)
                .build();
    }

    @Bean
    public org.springframework.amqp.core.Binding tradeEventForFinanceDelayQueueBinding() {
        return BindingBuilder.bind(tradeEventPreFinanceDelayQueue()).to(rabbitmqPublishFacade.tradeEventExchange());
    }
*/

    @RabbitListener(queues = {TRADE_EVENT_FOR_PRE_FINANCE})
    @RabbitHandler
    public void consumeTradeEventPreFinanceQueue(String value, Channel channel, Message message) throws IOException {
        LOG.info("rabbitmq 收到消息:{}", value);

        boolean fail = false;
        for (MessageQueueConsumerFacade messageQueueConsumerFacade : consumerMap.get(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName())) {
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
            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
        }
    }

}