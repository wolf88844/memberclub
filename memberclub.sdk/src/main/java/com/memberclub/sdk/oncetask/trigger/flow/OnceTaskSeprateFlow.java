/**
 * @(#)OnceTaskTriggerConcurrentFlow.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.google.common.collect.Lists;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.domain.context.oncetask.trigger.TriggerJobDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskSeprateFlow extends SubFlowNode<OnceTaskTriggerContext, OnceTaskTriggerContext> {


    @Override
    public void process(OnceTaskTriggerContext context) {
        //如果分库分表,应该按照实际的规模拆分任务.
        int dbNum = 1;
        int tableNum = 2;
        if (ApplicationContextUtils.isUnitTest()) {
            tableNum = 1;
        }
        List<TriggerJobDO> jobs = Lists.newArrayList();
        for (int i = 0; i < dbNum; i++) {
            for (int j = 0; j < tableNum; j++) {
                TriggerJobDO job = new TriggerJobDO();
                job.setDatabase(i);
                job.setTable(j);
                jobs.add(job);
            }
        }
        context.setJobs(jobs);
    }
}