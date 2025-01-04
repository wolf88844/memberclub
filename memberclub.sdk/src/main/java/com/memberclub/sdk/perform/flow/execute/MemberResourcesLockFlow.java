/**
 * @(#)MemberResourcesLockFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.sdk.perform.service.domain.PerformLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberResourcesLockFlow extends FlowNode<PerformContext> {

    @Autowired
    private PerformLockService performLockService;

    @Override
    public void process(PerformContext context) {
        String lockValue = performLockService.lock(context.getBizType(),
                context.getLockValue(),
                context.getUserId(),
                context.getTradeId());
        context.setLockValue(lockValue);
        context.getCmd().setLockValue(context.getLockValue());
    }


    @Override
    public void success(PerformContext context) {
        performLockService.unlock(context.getBizType(),
                context.getUserId(),
                context.getTradeId(),
                context.getLockValue());
    }

    @Override
    public void rollback(PerformContext context, Exception e) {
        performLockService.rollbackLock(context.getBizType(),
                context.getUserId(),
                context.getTradeId(),
                context.getLockValue(),
                context.getRetryTimes());
    }
}