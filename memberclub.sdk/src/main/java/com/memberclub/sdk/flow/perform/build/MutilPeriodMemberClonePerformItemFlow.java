/**
 * @(#)MutilPeriodMemberCopyPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.google.common.collect.Lists;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class MutilPeriodMemberClonePerformItemFlow extends FlowNode<PerformContext> {


    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            for (PerformItemDO immediatePerformItem : skuPerformContext.getImmediatePerformItems()) {
                if (immediatePerformItem.getCycle() <= 1) {
                    continue;
                }

                List<PerformItemDO> performItems = Lists.newArrayList();
                List<PerformItemDO> delayPerformItems = Lists.newArrayList();

                for (long i = immediatePerformItem.getCycle(); i > 0; i--) {
                    PerformItemDO temp = PerformConvertor.INSTANCE.copyPerformItem(immediatePerformItem);
                    temp.setPhase((int) i);
                    if (i == 1) {
                        performItems.add(temp);
                    } else {
                        delayPerformItems.add(temp);
                    }

                }

                skuPerformContext.setImmediatePerformItems(performItems);
                skuPerformContext.setDelayPerformItems(delayPerformItems);
            }
        }
    }
}