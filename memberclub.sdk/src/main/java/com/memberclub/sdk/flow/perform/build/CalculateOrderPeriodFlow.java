/**
 * @(#)CalculateOrderPeriodFlow.java, 十二月 20, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CalculateOrderPeriodFlow extends FlowNode<PerformContext> {

    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            long stime = context.getBaseTime();
            long etime = context.getImmediatePerformEtime();
            if (context.getDelayPerformEtime() > 0) {
                etime = context.getDelayPerformEtime();
            }

            skuPerformContext.setStime(stime);
            skuPerformContext.setEtime(etime);
        }
        context.setStime(context.getSkuPerformContexts().get(0).getStime());
        context.setEtime(context.getSkuPerformContexts().get(0).getEtime());
    }
}