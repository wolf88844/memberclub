/**
 * @(#)SingleMemberPerformHisFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.sdk.perform.extension.execute.MemberSubOrderPerformExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class SingleSubOrderPerformFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;
    
    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(PerformContext context) {
        SubOrderPerformContext subOrderPerformContext = context.getSubOrderPerformContexts().get(0);
        context.setCurrentSubOrderPerformContext(subOrderPerformContext);

        performDomainService.onStartPerformSubOrder(context, context.getCurrentSubOrderPerformContext());
    }


    @Override
    public void success(PerformContext context) {
        SubOrderPerformContext subOrderPerformContext = context.getSubOrderPerformContexts().get(0);
        MemberSubOrderPerformExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberSubOrderPerformExtension.class);
        extension.buildMemberSubOrderWhenPerformSuccess(context, subOrderPerformContext);

        performDomainService.onFinishSubOrderPerformOnSuccess(subOrderPerformContext.getSubOrder());
    }
}