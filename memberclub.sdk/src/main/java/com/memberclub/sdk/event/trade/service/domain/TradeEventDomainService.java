/**
 * @(#)TradeEventDomainService.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.event.trade.service.domain;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.reverse.PerformItemReverseInfo;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.event.trade.TradeEventDetailDO;
import com.memberclub.domain.dataobject.event.trade.TradeEventEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.infrastructure.mq.MQTopicEnum;
import com.memberclub.infrastructure.mq.MessageQuenePublishFacade;
import com.memberclub.sdk.event.trade.extension.TradeEventDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class TradeEventDomainService {

    @Autowired
    private MessageQuenePublishFacade messageQuenePublishFacade;

    @Autowired
    private ExtensionManager extensionManager;

    public void onPerformSuccessForSubOrder(PerformContext performContext,
                                            SubOrderPerformContext subOrderPerformContext,
                                            MemberSubOrderDO subOrder) {
        TradeEventDO event = buildTradeEvent(subOrder,
                CollectionUtilEx.mapToList(subOrderPerformContext.getImmediatePerformItems(), MemberPerformItemDO::getItemToken),
                TradeEventEnum.SUB_ORDER_PERFORM_SUCCESS,
                1
        );

        String value = extensionManager.getExtension(BizScene.of(subOrder.getBizType()),
                TradeEventDomainExtension.class).onPerformSuccessForSubOrder(performContext,
                subOrderPerformContext, subOrder, event);

        messageQuenePublishFacade.publish(MQTopicEnum.TRADE_EVENT, value);
    }

    public void onReversePerformSuccessForSubOrder(ReversePerformContext context,
                                                   SubOrderReversePerformContext subOrderReversePerformContext,
                                                   MemberSubOrderDO subOrder) {
        TradeEventDO event = buildTradeEvent(subOrder,
                CollectionUtilEx.mapToList(subOrderReversePerformContext.getItems(), PerformItemReverseInfo::getItemToken),
                TradeEventEnum.SUB_ORDER_RERVERSE_PERFORM_SUCCESS,
                context.getAfterSaleApplyContext().getPreviewContext().getPeriodIndex());
        String value = extensionManager.getExtension(BizScene.of(subOrder.getBizType()),
                TradeEventDomainExtension.class).onReversePerformSuccessForSubOrder(context,
                subOrderReversePerformContext, subOrder, event);

        messageQuenePublishFacade.publish(MQTopicEnum.TRADE_EVENT, value);
    }

    public void onRefundSuccessForSubOrder(AfterSaleApplyContext context,
                                           MemberSubOrderDO subOrder) {
        List<String> itemTokens = CollectionUtilEx.filterAndMap(
                context.getPreviewContext().getPerformItems(),
                (item) -> StringUtils.equals(String.valueOf(subOrder.getSubTradeId()), item.getSubTradeId()),
                MemberPerformItemDO::getItemToken
        );

        TradeEventDO event = buildTradeEvent(subOrder,
                itemTokens,
                TradeEventEnum.SUB_ORDER_REFUND_SUCCESS,
                context.getPreviewContext().getPeriodIndex());
        String value = extensionManager.getExtension(BizScene.of(subOrder.getBizType()),
                TradeEventDomainExtension.class).onRefundSuccessForSubOrder(context, subOrder, event);

        messageQuenePublishFacade.publish(MQTopicEnum.TRADE_EVENT, value);
    }

    public void onFreezeSuccessForSubOrder(AfterSaleApplyContext context,
                                           MemberSubOrderDO subOrder) {
        List<String> itemTokens = CollectionUtilEx.filterAndMap(
                context.getPreviewContext().getPerformItems(),
                (item) -> StringUtils.equals(String.valueOf(subOrder.getSubTradeId()), item.getSubTradeId()),
                MemberPerformItemDO::getItemToken
        );

        TradeEventDO event = buildTradeEvent(subOrder,
                itemTokens,
                TradeEventEnum.SUB_ORDER_FREEZE_SUCCESS,
                context.getPreviewContext().getPeriodIndex());
        String value = extensionManager.getExtension(BizScene.of(subOrder.getBizType()),
                TradeEventDomainExtension.class).onRefundSuccessForSubOrder(context, subOrder, event);

        messageQuenePublishFacade.publish(MQTopicEnum.TRADE_EVENT, value);
    }


    private TradeEventDO buildTradeEvent(MemberSubOrderDO subOrder, List<String> itemTokens, TradeEventEnum eventType, Integer periodIndex) {
        TradeEventDO event = new TradeEventDO();
        event.setEventType(eventType);
        TradeEventDetailDO detail = new TradeEventDetailDO();
        detail.setEventTime(subOrder.getUtime());
        detail.setSkuId(subOrder.getSkuId());
        detail.setTradeId(subOrder.getTradeId());
        detail.setBizType(subOrder.getBizType());
        detail.setSubTradeId(subOrder.getSubTradeId());
        detail.setUserId(subOrder.getUserId());
        detail.setPerformStatus(subOrder.getPerformStatus());
        detail.setPeriodIndex(periodIndex == null ? 1 : periodIndex);
        detail.setItemTokens(itemTokens);
        event.setDetail(detail);
        return event;
    }
}