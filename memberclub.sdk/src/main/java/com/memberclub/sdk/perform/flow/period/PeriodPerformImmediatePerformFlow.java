/**
 * @(#)PeriodPerformImmediatePerformFlow.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.period;

import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentItemDO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class PeriodPerformImmediatePerformFlow extends SubFlowNode<PeriodPerformContext, PerformItemContext> {

    @Autowired
    private FlowChainService flowChainService;

    @Override
    public void process(PeriodPerformContext context) {
        Map<Integer, List<PerformTaskContentItemDO>> rightType2Items = context.getContent().getItems()
                .stream().collect(Collectors.groupingBy(PerformTaskContentItemDO::getRightType));

        for (Map.Entry<Integer, List<PerformTaskContentItemDO>> entry : rightType2Items.entrySet()) {
            PerformItemContext itemContext = PerformConvertor.INSTANCE.toPerformItemContextForPeriodPerform(context);
            itemContext.setRightType(entry.getKey());
            itemContext.setPeriodPerform(true);

            List<MemberPerformItemDO> itemDOS = entry.getValue().stream()
                    .map(PerformConvertor.INSTANCE::toMemberPerformItemDOForPeriodPerform)
                    .collect(Collectors.toList());

            itemContext.setItems(itemDOS);
            flowChainService.execute(getSubChain(), itemContext);
        }
    }
}