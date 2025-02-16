/**
 * @(#)CalculateOrderPeriodFlow.java, 十二月 20, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CalculateOrderPeriodFlow extends FlowNode<PerformContext> {

    @Override
    public void process(PerformContext context) {
        for (SubOrderPerformContext subOrderPerformContext : context.getSubOrderPerformContexts()) {
            long stime = context.getBaseTime();
            long etime = context.getImmediatePerformEtime();
            if (context.getDelayPerformEtime() > 0) {
                etime = context.getDelayPerformEtime();
            }

            subOrderPerformContext.getSubOrder().setStime(stime);
            subOrderPerformContext.getSubOrder().setEtime(etime);
        }
        context.setStime(context.getSubOrderPerformContexts().get(0).getSubOrder().getStime());
        context.setEtime(context.getSubOrderPerformContexts().get(0).getSubOrder().getEtime());
    }
}