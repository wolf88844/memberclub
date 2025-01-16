/**
 * @(#)RabbitmqConsumerFacade.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.consumer.impl;

import com.memberclub.infrastructure.mq.MQContants;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import com.memberclub.infrastructure.mq.producer.impl.RabbitmqPublishFacadeImpl;
import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "rabbitmq", matchIfMissing = true)
@Service
public class RabbitmqConsumerConfiguration {

    public static final Logger LOG = LoggerFactory.getLogger(RabbitmqConsumerConfiguration.class);


    @Autowired
    private RabbitmqPublishFacadeImpl rabbitmqPublishFacade;

    @Bean
    public Queue tradeEventPreFinanceQueue() {
        return new Queue(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName(), true);
    }

    @Bean
    public Binding tradeEventForFinanceQueueBinding() {
        return BindingBuilder.bind(tradeEventPreFinanceQueue()).to(rabbitmqPublishFacade.tradeEventExchange());
    }

    @RabbitListener(queues = MQContants.TRADE_EVENT_FOR_PRE_FINANCE)
    @RabbitHandler
    public void consumeTradeEventPreFinanceQueue(String value, Channel channel, Message message) throws IOException {
        LOG.info("rabbitmq 收到消息:{}", value);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}