/**
 * @(#)LocalMessageQueueConsumeFacade.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.service;

import com.google.common.collect.Sets;
import com.memberclub.infrastructure.mq.MQEventEnum;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * author: 掘金五阳
 */
@Service
public class PreFinanceOnTradeEventConsumer implements MessageQueueConsumerFacade {

    public static final Logger LOG = LoggerFactory.getLogger(PreFinanceOnTradeEventConsumer.class);


    @Override
    public void consume(MQEventEnum event, String message) {
        LOG.info("收到 event:{}, message:{}", event, message);
    }

    @Override
    public Set<MQEventEnum> register() {
        return Sets.newHashSet(MQEventEnum.TRADE_EVENT);
    }
}