/**
 * @(#)DelayPerformFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute.delay;

import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class DelayPerformFlow extends SubFlowNode<PerformContext, DelayItemContext> {

    @Autowired
    private FlowChainService flowChainService;

    @Override
    public void process(PerformContext context) {
        if (CollectionUtils.isEmpty(context.getCurrentSkuPerformContext().getDelayPerformItems())) {
            return;
        }

        Map<Integer, List<MemberPerformItemDO>> itemMap = context.getCurrentSkuPerformContext()
                .getImmediatePerformItems().stream()
                .collect(Collectors.groupingBy((item) -> item.getRightType().toInt()));

        for (Map.Entry<Integer, List<MemberPerformItemDO>> entry : itemMap.entrySet()) {
            DelayItemContext itemContext = new DelayItemContext();
            itemContext.setItems(entry.getValue());
            itemContext.setPerformContext(context);
            itemContext.setRightType(entry.getKey());
            itemContext.setSkuPerformContext(context.getCurrentSkuPerformContext());
            flowChainService.execute(getSubChain(), itemContext);
        }
    }
}