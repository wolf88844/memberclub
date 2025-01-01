/**
 * @(#)BuildDelayPerformOnceTaskFlow.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute.delay;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.sdk.perform.extension.delay.DelayOnceTaskExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class BuildDelayPerformOnceTaskFlow extends FlowNode<DelayItemContext> {


    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(DelayItemContext context) {
        Map<Integer, List<MemberPerformItemDO>> phase2Item =
                context.getItems()
                        .stream().collect(Collectors.groupingBy(MemberPerformItemDO::getPhase));
        List<OnceTaskDO> tasks = Lists.newArrayList();

        for (Map.Entry<Integer, List<MemberPerformItemDO>> entry : phase2Item.entrySet()) {
            OnceTaskDO task = extensionManager.getExtension(context.getPerformContext().toDefaultScene(),
                    DelayOnceTaskExtension.class).buildTask(context, entry.getValue());
            tasks.add(task);
        }
        context.setTasks(tasks);
    }
}