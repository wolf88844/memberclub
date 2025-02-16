/**
 * @(#)OnceTaskForceRouterFlow.java, 二月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerJobContext;
import org.apache.shardingsphere.infra.hint.HintManager;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskForceRouterFlow extends FlowNode<OnceTaskTriggerJobContext> {


    @Override
    public void process(OnceTaskTriggerJobContext context) {
        HintManager hintManager = HintManager.getInstance();
        hintManager.addTableShardingValue("once_task_hint", context.getJob().getTable());
        //hintManager.add
        context.setHintManager(hintManager);
    }


    @Override
    public void callback(OnceTaskTriggerJobContext onceTaskTriggerJobContext, Exception e) {
        ((HintManager) onceTaskTriggerJobContext.getHintManager()).close();
    }
}