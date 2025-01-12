/**
 * @(#)LocalMessageQueneFacade.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.mq.MQEventEnum;
import com.memberclub.infrastructure.mq.MessageQuenePublishFacade;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import org.apache.commons.collections.MapUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: 掘金五阳
 */

@ConditionalOnProperty(name = "memberclub.infrastructure.mq", havingValue = "local", matchIfMissing = false)
@Service
public class LocalMessageQuenePublishFacade implements MessageQuenePublishFacade {

    private ExecutorService executorService = Executors.newFixedThreadPool(2);

    private Map<MQEventEnum, List<MessageQueueConsumerFacade>> consumerMap = Maps.newHashMap();

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
                Set<MQEventEnum> eventEnums = entry.getValue().register();
                for (MQEventEnum mqEventEnum : eventEnums) {
                    consumerMap.putIfAbsent(mqEventEnum, Lists.newArrayList());
                    consumerMap.get(mqEventEnum).add(entry.getValue());
                }
            }
        }
    }


    @Retryable()
    @Override
    public void publish(MQEventEnum event, String message) {
        executorService.execute(() -> {
            if (consumerMap.containsKey(event)) {
                for (MessageQueueConsumerFacade messageQueueConsumerFacade : consumerMap.get(event)) {
                    messageQueueConsumerFacade.consume(event, message);
                }
            }
        });
    }
}