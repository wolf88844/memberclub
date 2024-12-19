/**
 * @(#)ImmdiatePerformFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
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
    private ExtensionManger extensionManger;

    @Autowired
    private FlowChainService flowChainService;

    @Override
    public void process(PerformContext context) {
        Map<Integer, List<PerformItemDO>> itemMap = context.getCurrentSkuPerformContext()
                .getImmediatePerformItems().stream()
                .collect(Collectors.groupingBy(PerformItemDO::getRightType));

        for (Map.Entry<Integer, List<PerformItemDO>> entry : itemMap.entrySet()) {
            PerformItemContext itemContext = new PerformItemContext();
            itemContext.setItems(entry.getValue());
            itemContext.setPerformContext(context);
            itemContext.setSkuPerformContext(context.getCurrentSkuPerformContext());
            flowChainService.execute(getSubChain(), itemContext);
        }
    }

}