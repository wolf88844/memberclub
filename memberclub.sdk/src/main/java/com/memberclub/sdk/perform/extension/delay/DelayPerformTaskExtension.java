/**
 * @(#)DelayOnceTaskExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.delay;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;

import java.util.List;

/**
 * @author wuyang
 */
@ExtensionConfig(desc = "延迟履约任务扩展点", type = ExtensionType.PERIOD_PERFORM, must = false)
public interface DelayPerformTaskExtension extends BaseExtension {

    public OnceTaskDO buildTask(DelayItemContext context, List<MemberPerformItemDO> items);
}