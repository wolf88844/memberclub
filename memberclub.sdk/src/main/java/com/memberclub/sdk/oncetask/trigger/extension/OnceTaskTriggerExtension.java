/**
 * @(#)OnceTaskTriggerExtension.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.oncetask.execute.OnceTaskExecuteContext;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;

/**
 * author: 掘金五阳
 * 扩展 Scene
 *
 * @see com.memberclub.domain.context.oncetask.common.TaskTypeEnum
 */
@ExtensionConfig(desc = "任务触发扩展点", must = false, type = ExtensionType.COMMON)
public interface OnceTaskTriggerExtension extends BaseExtension {

    public void trigger(OnceTaskTriggerContext context);

    public void execute(OnceTaskExecuteContext context);
}