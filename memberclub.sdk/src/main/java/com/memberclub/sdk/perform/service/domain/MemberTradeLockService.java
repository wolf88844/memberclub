/**
 * @(#)LockService.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.common.LockContext;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.infrastructure.lock.DistributeLock;
import com.memberclub.sdk.config.extension.BizConfigTable;
import com.memberclub.sdk.config.service.ConfigService;
import com.memberclub.sdk.lock.LockService;
import com.memberclub.sdk.lock.extension.LockExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberTradeLockService {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private LockService lockService;


    @Autowired
    private DistributeLock distributeLock;

    @Autowired
    private ConfigService configService;

    public LockExtension getLockExtension(BizTypeEnum bizTypeEnum) {
        return extensionManager.getExtension(BizScene.of(bizTypeEnum), LockExtension.class);
    }

    public void lockOnPrePerform(PerformContext context) {
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().
                bizType(context.getBizType()).
                lockScene("perform").
                lockMode(table.getLockMode()).
                userId(context.getUserId()).
                tradeId(context.getTradeId()).
                build();
        getLockExtension(context.getBizType()).buildOnPrePerform(lockContext, context);

        Long lockValue = lockService.lock(lockContext);

        context.setLockValue(lockValue);
        context.getCmd().setLockValue(context.getLockValue());
    }


    public void unlockOnPerformSuccess(PerformContext context) {
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().bizType(context.getBizType())
                .lockScene("perform")
                .lockMode(table.getLockMode())
                .userId(context.getUserId())
                .tradeId(context.getTradeId())
                .lockValue(context.getLockValue())
                .build();
        getLockExtension(context.getBizType()).buildOnPerformSuccess(lockContext, context);

        lockService.unlock(lockContext);
    }

    public void unlockOnPerformFail(PerformContext context) {
        CommonLog.error("回滚阶段尝试解锁");
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().bizType(context.getBizType())
                .lockScene("perform")
                .lockMode(table.getLockMode())
                .userId(context.getUserId())
                .tradeId(context.getTradeId())
                .lockValue(context.getLockValue())
                .build();
        getLockExtension(context.getBizType()).buildOnPerformFail(lockContext, context);
        if (lockContext.isUnlockOnPerformFail()) {
            lockService.unlock(lockContext);
        }
    }

    /************************************ 周期履约 ********************************************/
    public void lockOnPrePeriodPerform(PeriodPerformContext context) {
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().
                bizType(context.getBizType()).
                lockScene("period_perform").
                lockMode(table.getLockMode()).
                userId(context.getUserId()).
                tradeId(context.getTradeId()).
                build();
        getLockExtension(context.getBizType()).buildOnPrePeriodPerform(lockContext, context);

        Long lockValue = lockService.lock(lockContext);

        context.setLockValue(lockValue);
    }

    public void unlockOnPeriodPerformSuccess(PeriodPerformContext context) {
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().
                bizType(context.getBizType()).
                lockScene("period_perform").
                lockMode(table.getLockMode()).
                userId(context.getUserId()).
                lockValue(context.getLockValue()).
                tradeId(context.getTradeId()).
                build();
        getLockExtension(context.getBizType()).buildOnPeriodPerformSuccess(lockContext, context);

        lockService.unlock(lockContext);
    }

    public void unlockOnPeriodPerformFail(PeriodPerformContext context) {
        CommonLog.error("周期履约回滚阶段尝试解锁");
        BizConfigTable table = configService.findConfigTable(BizScene.of(context.getBizType()));

        LockContext lockContext = LockContext.builder().bizType(context.getBizType())
                .lockScene("perform")
                .lockMode(table.getLockMode())
                .userId(context.getUserId())
                .tradeId(context.getTradeId())
                .lockValue(context.getLockValue())
                .build();
        getLockExtension(context.getBizType()).buildOnPeriodPerformFail(lockContext, context);
        if (lockContext.isUnlockOnPeriodPerformFail()) {
            lockService.unlock(lockContext);
        }
    }
    /************************************ 周期履约 ********************************************/
}