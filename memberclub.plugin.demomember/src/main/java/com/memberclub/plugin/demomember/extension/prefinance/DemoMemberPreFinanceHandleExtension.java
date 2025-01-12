/**
 * @(#)DemoMemberPreFinanceHandleExtension.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.prefinance;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.prefinance.PreFinanceContext;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.event.trade.TradeEventEnum;
import com.memberclub.domain.dataobject.prefinance.FinanceEventEnum;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.sdk.prefinance.extension.PreFinanceHandleExtension;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 预结算处理扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER)
})
public class DemoMemberPreFinanceHandleExtension implements PreFinanceHandleExtension {

    private static Table<TradeEventEnum, SubOrderPerformStatusEnum, FinanceEventEnum> financeEventEnumTable = HashBasedTable.create();

    static {
        financeEventEnumTable.put(TradeEventEnum.SUB_ORDER_PERFORM_SUCCESS, SubOrderPerformStatusEnum.PERFORM_SUCC, FinanceEventEnum.PERFORM);
        financeEventEnumTable.put(TradeEventEnum.SUB_ORDER_REFUND_SUCCESS, SubOrderPerformStatusEnum.COMPLETED_REVERSED, FinanceEventEnum.REFUND);
        financeEventEnumTable.put(TradeEventEnum.SUB_ORDER_REFUND_SUCCESS, SubOrderPerformStatusEnum.PORTION_REVERSED, FinanceEventEnum.REFUND);
    }

    private static Map<FinanceEventEnum, FlowChain<PreFinanceContext>> financeEventChainMap = Maps.newHashMap();


    @PostConstruct
    public void init() {
        FlowChain<PreFinanceContext> performChain = FlowChain.newChain(PreFinanceContext.class)
                //.addNode()
                ;


        FlowChain<PreFinanceContext> freezeChain = FlowChain.newChain(PreFinanceContext.class)
                //.addNode()
                ;


        FlowChain<PreFinanceContext> refundChain = FlowChain.newChain(PreFinanceContext.class)
                //.addNode()
                ;
        financeEventChainMap.put(FinanceEventEnum.PERFORM, performChain);
        financeEventChainMap.put(FinanceEventEnum.FREEZE_NON_REFUND, freezeChain);
        financeEventChainMap.put(FinanceEventEnum.REFUND, refundChain);
    }


    @Override
    @UserLog(domain = LogDomainEnum.PRE_FINANCE, userId = "detail.userId", tradeId = "detai.tradeId", bizType = "detail.bizType")
    public void handle(TradeEventDO event) {
        FinanceEventEnum financeEventEnum = financeEventEnumTable.get(event.getEventType(), event.getDetail().getPerformStatus());
        if (financeEventEnum == null) {
            CommonLog.info("该事件无需预结算处理 eventType:{}, performStatus:{}", event.getEventType(), event.getDetail().getPerformStatus());
            return;
        }

        PreFinanceContext context = new PreFinanceContext();
        context.setEvent(event);
        context.setFinanceEventEnum(financeEventEnum);

        FlowChain<PreFinanceContext> chain = financeEventChainMap.get(financeEventEnum);
        if (chain == null) {
            CommonLog.error("没有找到预结算流程 FinanceEventEnum:{}, event:{} ", financeEventEnum, event);
            throw ResultCode.INTERNAL_ERROR.newException(String.format("没有找到预结算流程:%s", financeEventEnum));
        }
        CommonLog.info("预结算开始受理 FinanceEventEnum:{}, event:{}", financeEventEnum, event);
        chain.execute(context);
    }
}