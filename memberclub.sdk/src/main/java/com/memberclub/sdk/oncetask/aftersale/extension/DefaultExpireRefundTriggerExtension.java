/**
 * @(#)DefaultExpireRefundTriggerExtension.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.aftersale.extension;

import com.google.common.collect.ImmutableList;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.domain.context.oncetask.execute.OnceTaskExecuteContext;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerJobContext;
import com.memberclub.sdk.oncetask.aftersale.flow.ExpiredRefundOnceTaskExecuteFlow;
import com.memberclub.sdk.oncetask.execute.OnceTaskRepositoryFlow;
import com.memberclub.sdk.oncetask.trigger.extension.OnceTaskTriggerExtension;
import com.memberclub.sdk.oncetask.trigger.flow.OnceTaskConcurrentTriggerFlow;
import com.memberclub.sdk.oncetask.trigger.flow.OnceTaskScanDataFlow;
import com.memberclub.sdk.oncetask.trigger.flow.OnceTaskSeprateFlow;
import com.memberclub.sdk.oncetask.trigger.flow.OnceTaskTriggerMonitorFlow;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
public abstract class DefaultExpireRefundTriggerExtension implements OnceTaskTriggerExtension {
    private FlowChain<OnceTaskTriggerContext> triggerFlowChain = null;

    private FlowChain<OnceTaskExecuteContext> executelowChain = null;

    @PostConstruct
    public void init() {
        triggerFlowChain = FlowChain.newChain(OnceTaskTriggerContext.class)
                .addNode(OnceTaskSeprateFlow.class)
                .addNodeWithSubNodes(OnceTaskConcurrentTriggerFlow.class, OnceTaskTriggerJobContext.class,
                        ImmutableList.of(OnceTaskScanDataFlow.class)
                )
                .addNode(OnceTaskTriggerMonitorFlow.class)
        ;

        executelowChain = FlowChain.newChain(OnceTaskExecuteContext.class)
                .addNode(OnceTaskRepositoryFlow.class)
                .addNode(ExpiredRefundOnceTaskExecuteFlow.class)
        ;
    }


    @Override
    public void trigger(OnceTaskTriggerContext context) {
        triggerFlowChain.execute(context);
    }

    @Override
    public void execute(OnceTaskExecuteContext context) {
        executelowChain.execute(context);
    }
}