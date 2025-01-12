/**
 * @(#)PeriodPerformExecuteExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.period;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "履约周期履约扩展点", type = ExtensionType.PERIOD_PERFORM, must = false)
public interface PeriodPerformExecuteExtension extends BaseExtension {


    public void buildContext(OnceTaskDO task, PeriodPerformContext context);

    public void periodPerform(PeriodPerformContext context);
}