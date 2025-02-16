/**
 * @(#)ImmdiatePerformFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class ImmediatePerformFlow extends SubFlowNode<PerformContext, PerformItemContext> {

    @Autowired
    private FlowChainService flowChainService;

    @Override
    public void process(PerformContext context) {
        Map<Integer, List<MemberPerformItemDO>> itemMap = context.getCurrentSubOrderPerformContext()
                .getImmediatePerformItems().stream()
                .collect(Collectors.groupingBy((item) -> item.getRightType().getCode()));

        for (Map.Entry<Integer, List<MemberPerformItemDO>> entry : itemMap.entrySet()) {
            PerformItemContext itemContext = new PerformItemContext();
            itemContext.setItems(entry.getValue());
            itemContext.setUserId(context.getUserId());
            itemContext.setBizType(context.getBizType());
            itemContext.setTradeId(context.getTradeId());
            itemContext.setSubTradeId(String.valueOf(context.getCurrentSubOrderPerformContext().getSubOrder().getSubTradeId()));
            itemContext.setRightType(entry.getKey());
            itemContext.setPeriodPerform(false);
            itemContext.setSkuId(context.getCurrentSubOrderPerformContext().getSubOrder().getSkuId());

            flowChainService.execute(getSubChain(), itemContext);
        }
    }

}