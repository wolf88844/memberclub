/**
 * @(#)PeriodPerformExecuteExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.period;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;

/**
 * author: 掘金五阳
 */
public interface PeriodPerformExecuteExtension extends BaseExtension {


    public void buildContext(OnceTaskDO task, PeriodPerformContext context);

    public void periodPerform(PeriodPerformContext context);
}