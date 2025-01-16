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
import com.memberclub.infrastructure.mq.MQContants;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import com.memberclub.infrastructure.mq.producer.impl.RabbitmqPublishFacadeImpl;
import com.rabbitmq.client.Channel;
import org.apache.commons.collections.MapUtils;
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

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "rabbitmq", matchIfMissing = true)
@Service
public class RabbitmqConsumerConfiguration {

    public static final Logger LOG = LoggerFactory.getLogger(RabbitmqConsumerConfiguration.class);


    @Autowired
    private RabbitmqPublishFacadeImpl rabbitmqPublishFacade;

    private Map<String, List<MessageQueueConsumerFacade>> consumerMap = Maps.newHashMap();

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

        for (MessageQueueConsumerFacade messageQueueConsumerFacade : consumerMap.get(MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE.getQueneName())) {
            messageQueueConsumerFacade.consume(value);
        }

        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}