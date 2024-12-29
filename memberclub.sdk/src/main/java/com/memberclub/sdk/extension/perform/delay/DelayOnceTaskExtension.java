/**
 * @(#)DelayOnceTaskExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.delay;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;

/**
 * @author yuhaiqiang
 */
public interface DelayOnceTaskExtension extends BaseExtension {

    public OnceTaskDO buildTask(PerformContext context, MemberPerformItemDO item);
}