/**
 * @(#)OnceTaskDomainExtension.java, 一月 11, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.periodperform.extension;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.entity.trade.OnceTask;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "履约周期任务 Domain 层扩展点", type = ExtensionType.PERIOD_PERFORM, must = false)
public interface PeriodPerformTaskDomainExtension extends BaseExtension {

    public void onCreate(DelayItemContext context, List<OnceTask> tasks);

    public void onCancel(ReversePerformContext reversePerformContext,
                         SubOrderReversePerformContext context,
                         LambdaUpdateWrapper<OnceTask> wrapper);
}