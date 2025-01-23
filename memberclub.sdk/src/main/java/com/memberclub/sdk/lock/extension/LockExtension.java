/**
 * @(#)LockExtension.java, 一月 23, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.lock.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.common.LockContext;
import com.memberclub.domain.context.common.LockMode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.sdk.common.SwitchEnum;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "加减锁扩展点", type = ExtensionType.COMMON, must = false)
public interface LockExtension extends BaseExtension {

    default void buildOnPrePerform(LockContext context, PerformContext performContext) {

    }

    default void buildOnPerformSuccess(LockContext context, PerformContext performContext) {

    }

    default void buildOnPerformFail(LockContext context, PerformContext performContext) {
        if (context.getLockMode() == LockMode.LOCK_ORDER) {
            context.setUnlockOnPerformFail(true);
        } else if (context.getLockMode() == LockMode.LOCK_USER) {
            if (performContext.getRetryTimes() >= SwitchEnum.PERFORM_RETRY_MAX_TIME.getInt(context.getBizType().getCode())) {
                CommonLog.warn("用户维度锁,已达最大履约重试次数, 释放锁");
                context.setUnlockOnPerformFail(true);
            } else {
                CommonLog.warn("用户维度锁,在履约失败时,暂时不释放");
                context.setUnlockOnPerformFail(false);
            }
        }
    }

    default void buildOnPrePeriodPerform(LockContext context, PeriodPerformContext performContext) {

    }

    default void buildOnPeriodPerformSuccess(LockContext context, PeriodPerformContext performContext) {

    }

    default void buildOnPeriodPerformFail(LockContext context, PeriodPerformContext performContext) {
        context.setUnlockOnPeriodPerformFail(true);
    }
}