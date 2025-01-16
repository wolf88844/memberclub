/**
 * @(#)RabbitmqPublishFacadeImpl.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.producer.impl;

import com.memberclub.infrastructure.mq.MQContants;
import com.memberclub.infrastructure.mq.MQTopicEnum;
import com.memberclub.infrastructure.mq.MessageQuenePublishFacade;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "rabbitmq", matchIfMissing = true)
@Configuration
public class RabbitmqPublishFacadeImpl implements MessageQuenePublishFacade {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        return rabbitTemplate;
    }

    @Bean
    public FanoutExchange tradeEventExchange() {
        return new FanoutExchange(MQContants.TRADE_EVENT_TOPIC, true, false);
    }

    @Override
    public void publish(MQTopicEnum event, String message) {
        rabbitTemplate.convertAndSend(event.toString(), "*", message);
    }
}