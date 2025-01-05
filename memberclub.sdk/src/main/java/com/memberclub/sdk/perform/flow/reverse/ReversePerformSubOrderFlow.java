/**
 * @(#)ReversePerformPerformHisFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.reverse;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.flow.SkipException;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class ReversePerformSubOrderFlow extends FlowNode<ReversePerformContext> {

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(ReversePerformContext context) {
        if (context.getCurrentSubOrderReverseInfo() == null) {
            context.setCurrentSubOrderReverseInfo(context.getReverseInfos().get(0));
        }
        if (performDomainService.isFinishReverseMemberSubOrder(context, context.getCurrentSubOrderReverseInfo())) {
            throw new SkipException("已经完成更新履约单,不再重试");
        }
        performDomainService.startReversePerformMemberSubOrder(context, context.getCurrentSubOrderReverseInfo());
    }

    @Override
    public void success(ReversePerformContext context) {
        MemberSubOrderDO subOrder = context.getCurrentSubOrderReverseInfo().getMemberSubOrder();

        subOrder.finishReversePerform(context.getCurrentSubOrderReverseInfo().isAllRefund());

        performDomainService.finishReversePerformMemberSubOrder(context, context.getCurrentSubOrderReverseInfo());
    }
}