/**
 * @(#)BuildDelayPerformOnceTaskFlow.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute.delay;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.sdk.extension.perform.delay.DelayOnceTaskExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class BuildDelayPerformOnceTaskFlow extends FlowNode<DelayItemContext> {


    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(DelayItemContext context) {
        for (MemberPerformItemDO delayItem : context.getSkuPerformContext().getDelayPerformItems()) {

            OnceTaskDO task = extensionManager.getExtension(context.getPerformContext().toDefaultScene(),
                    DelayOnceTaskExtension.class).buildTask(context.getPerformContext(), delayItem);
        }
    }
}