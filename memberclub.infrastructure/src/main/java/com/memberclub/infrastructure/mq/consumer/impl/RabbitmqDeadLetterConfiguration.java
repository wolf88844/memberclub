/**
 * @(#)RabbitmqDeadLetterConfiguration.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.consumer.impl;

import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "rabbitmq", matchIfMissing = true)
@Configuration
public class RabbitmqDeadLetterConfiguration {

    /**
     * 死信队列 交换机标识符
     */
    public static final String X_DEAD_LETTER_EXCHANGE = "x-dead-letter-exchange";
    /**
     * 死信队列交换机routing-key标识符
     */
    public static final String X_DEAD_LETTER_ROUTING_KEY = "x-dead-letter-routing-key";
    /**
     * 死信队列消息的超时时间枚举
     */
    public static final String X_MESSAGE_TTL = "x-message-ttl";

    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";

    public static final String DEAD_LETTER_QUEUE = "dead_letter_queue";

    @Bean
    public TopicExchange deadLetterExchange() {
        return (TopicExchange) ExchangeBuilder.topicExchange(DEAD_LETTER_EXCHANGE).durable(true).build();
    }

}