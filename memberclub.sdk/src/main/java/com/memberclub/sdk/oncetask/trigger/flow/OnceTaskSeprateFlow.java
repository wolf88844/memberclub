/**
 * @(#)OnceTaskTriggerConcurrentFlow.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.google.common.collect.Lists;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.domain.context.oncetask.trigger.TriggerJobDO;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskSeprateFlow extends SubFlowNode<OnceTaskTriggerContext, OnceTaskTriggerContext> {


    @Override
    public void process(OnceTaskTriggerContext context) {
        //如果分库分表,应该按照实际的规模拆分任务.
        TriggerJobDO job = new TriggerJobDO();
        job.setDatabase(0);
        job.setTable(0);
        context.setJobs(Lists.newArrayList(job));
    }
}