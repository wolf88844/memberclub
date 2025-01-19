/**
 * @(#)CommonRabbitmqRetryableQueue.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.consumer.impl;

import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CommonRabbitmqRetryableQueue {

    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";

    public TopicExchange deadLetterExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

    public void registerRetryableQueue(MQQueueEnum queue) {
        ConfigurableListableBeanFactory beanFactory =
                ((ConfigurableApplicationContext) ApplicationContextUtils.getContext()).getBeanFactory();

        Queue normalQueue = QueueBuilder.durable(queue.getQueneName()).build();
        Binding normalBinding2DeadExchange = BindingBuilder.bind(normalQueue)
                .to(deadLetterExchange())
                .with(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName());

        Queue delayQueue = QueueBuilder.durable(queue.getDelayQueneName())
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey(queue.getQueneName())
                .ttl(5000)
                .build();

        FanoutExchange exchange = new FanoutExchange(queue.getTopicName(), true, false);

        Binding delayQueue2NormalExchange = BindingBuilder.bind(delayQueue).to(exchange);

        beanFactory.registerSingleton(queue.getQueneName(), normalQueue);
        beanFactory.registerSingleton(queue.getQueneName() + "2DeadLetter", normalBinding2DeadExchange);
        beanFactory.registerSingleton(queue.getDelayQueneName(), delayQueue);
        beanFactory.registerSingleton(queue.getTopicName() + "." + queue.getQueneName(), exchange);
        beanFactory.registerSingleton(queue.getDelayQueneName() + "2NormalExchange", delayQueue2NormalExchange);
    }
}