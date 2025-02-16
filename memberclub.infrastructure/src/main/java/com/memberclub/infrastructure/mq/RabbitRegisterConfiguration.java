/**
 * @(#)RabbitRegisterConfiguration.java, 一月 20, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * author: 掘金五阳
 */
@Configuration
public class RabbitRegisterConfiguration {


    public static final String DEAD_LETTER_EXCHANGE = "dead_letter_exchange";

    @Bean
    public FanoutExchange tradeEventExchange() {
        return newFanoutExchange(MQContants.TRADE_EVENT_TOPIC);
    }

    @Bean
    public FanoutExchange preFinanceExchange() {
        return new FanoutExchange(MQTopicEnum.PRE_FINANCE_EVENT.getName());
    }

    public static FanoutExchange newFanoutExchange(String topic) {
        return new FanoutExchange(topic, true, false);
    }


    @Bean
    public TopicExchange deadLetterExchange() {
        return newTopicExchnage(DEAD_LETTER_EXCHANGE);
    }

    public static TopicExchange newTopicExchnage(String topic) {
        return (TopicExchange) ExchangeBuilder.topicExchange(topic).durable(true).build();
    }


    @Configuration
    static class TradeEventPreFinanceQueueConfiguration extends DelayRetryableQueueConfiguration {

        @Override
        public MQQueueEnum getQueue() {
            return MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE;
        }

        @Bean("TRADE_EVENT_FOR_PRE_FINANCE")
        @Override
        public Queue newNormalQueue() {
            return super.newNormalQueue();
        }

        @Bean("TRADE_EVENT_FOR_PRE_FINANCE_BINDING_NORMAL_EXCHANGE")
        @Override
        public Binding normalQueueBindingNormalExchange() {
            return super.normalQueueBindingNormalExchange();
        }

        @Bean("TRADE_EVENT_FOR_PRE_FINANCE_BINDING_DEAD_EXCHANGE")
        @Override
        public Binding normalQueueBindingDeadExchange() {
            return super.normalQueueBindingDeadExchange();
        }

        @Bean("TRADE_EVENT_FOR_PRE_FINANCE_DELAY_QUEUE")
        @Override
        public Queue delayQueue() {
            return super.delayQueue();
        }

        @Bean("TRADE_EVENT_FOR_PRE_FINANCE_DELAY_QUEUE_BINDING_DELAY_EXCHANGE")
        @Override
        public Binding delayQueueBindingDelayExchange() {
            return super.delayQueueBindingDelayExchange();
        }
    }


    static abstract class DelayRetryableQueueConfiguration {

        public abstract MQQueueEnum getQueue();

        public org.springframework.amqp.core.Queue newNormalQueue() {
            return QueueBuilder.durable(getQueue().getQueneName()).build();
        }

        public org.springframework.amqp.core.Binding normalQueueBindingNormalExchange() {
            return BindingBuilder.bind(newNormalQueue())
                    .to(newFanoutExchange(getQueue().getTopicName()));
        }

        public org.springframework.amqp.core.Binding normalQueueBindingDeadExchange() {
            return BindingBuilder.bind(newNormalQueue())
                    .to(newTopicExchnage(DEAD_LETTER_EXCHANGE))
                    .with(getQueue().getQueneName());
        }

        public org.springframework.amqp.core.Queue delayQueue() {
            return QueueBuilder.durable(getQueue().getDelayQueneName())
                    .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                    .deadLetterRoutingKey(getQueue().getQueneName())
                    .ttl((int) getQueue().getDelayMillSeconds())
                    .build();
        }

        public org.springframework.amqp.core.Binding delayQueueBindingDelayExchange() {
            return BindingBuilder.bind(delayQueue())
                    .to(newTopicExchnage(DEAD_LETTER_EXCHANGE))
                    .with(getQueue().getDelayQueneName());
        }

    }
}