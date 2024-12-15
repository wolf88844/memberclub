/**
 * @(#)CalculateDelayPerformItemPeriodFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CalculateDelayPerformItemPeriodFlow extends FlowNode<PerformContext> {


    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            for (PerformItemDO immediatePerformItem : skuPerformContext.getImmediatePerformItems()) {
                // TODO: 2024/12/15
            }
        }
    }
}