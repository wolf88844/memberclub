/**
 * @(#)StartPerformUpdteMemberOrderFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.sdk.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class StartPerformUpdteMemberOrderFlow extends FlowNode<PerformContext> {

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(PerformContext context) {
        int count = performDomainService.updateMemberOrderPerforming(context);
        context.setMemberOrderStartPerformUpdateCount(count);
    }
}