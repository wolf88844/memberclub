/**
 * @(#)OnceTaskTriggerMonitorFlow.java, 一月 28, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.sdk.oncetask.trigger.OnceTaskDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskTriggerMonitorFlow extends FlowNode<OnceTaskTriggerContext> {

    @Autowired
    private OnceTaskDomainService onceTaskDomainService;

    @Override
    public void process(OnceTaskTriggerContext onceTaskTriggerContext) {

    }

    @Override
    public void callback(OnceTaskTriggerContext context, Exception e) {
        onceTaskDomainService.monitor(context, e);
    }
}