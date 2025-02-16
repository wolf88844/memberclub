/**
 * @(#)PeriodPerformResourceLockFlow.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.period;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.sdk.perform.service.domain.MemberTradeLockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PeriodPerformResourceLockFlow extends FlowNode<PeriodPerformContext> {

    @Autowired
    private MemberTradeLockService memberTradeLockService;

    @Override
    public void process(PeriodPerformContext context) {
        memberTradeLockService.lockOnPrePeriodPerform(context);
    }

    @Override
    public void success(PeriodPerformContext context) {
        memberTradeLockService.unlockOnPeriodPerformSuccess(context);
    }

    @Override
    public void rollback(PeriodPerformContext context, Exception e) {
        memberTradeLockService.unlockOnPeriodPerformFail(context);
    }
}