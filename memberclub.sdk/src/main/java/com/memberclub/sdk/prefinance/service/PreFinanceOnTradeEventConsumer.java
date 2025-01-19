/**
 * @(#)LocalMessageQueueConsumeFacade.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.service;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.event.trade.TradeEvent;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.event.trade.TradeEventDetailDO;
import com.memberclub.domain.dataobject.event.trade.TradeEventEnum;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.CommonConvertor;
import com.memberclub.infrastructure.mq.ConsumeStatauEnum;
import com.memberclub.infrastructure.mq.MQQueueEnum;
import com.memberclub.infrastructure.mq.MessageQueueConsumerFacade;
import com.memberclub.sdk.prefinance.extension.PreFinanceHandleExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PreFinanceOnTradeEventConsumer implements MessageQueueConsumerFacade {

    public static final Logger LOG = LoggerFactory.getLogger(PreFinanceOnTradeEventConsumer.class);

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public ConsumeStatauEnum consume(String message) {
        LOG.info("收到  message:{}", message);
        TradeEventDO event = buildEvent(message);
        extensionManager.getExtension(BizScene.of(event.getDetail().getBizType()),
                PreFinanceHandleExtension.class).handle(event);
        return success;
    }

    private TradeEventDO buildEvent(String message) {
        try {
            TradeEvent event = JsonUtils.fromJson(message, TradeEvent.class);
            TradeEventDetailDO detail = CommonConvertor.INSTANCE.toTradeEventDetailDO(event.getDetail());
            TradeEventDO eventDO = new TradeEventDO();
            eventDO.setEventType(TradeEventEnum.findByCode(event.getEventType()));
            eventDO.setDetail(detail);
            return eventDO;
        } catch (Exception e) {
            LOG.info("解析构建 TradeEventDO 异常:{}", message, e);
            throw ResultCode.EXTRACT_MESSAGE_ERROR.newException("解析TradeEventDO异常", e);
        }
    }

    @Override
    public MQQueueEnum register() {
        return MQQueueEnum.TRADE_EVENT_FOR_PRE_FINANCE;
    }
}