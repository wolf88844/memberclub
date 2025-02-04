/**
 * @(#)OnceTaskTriggerJobContext.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.oncetask.trigger;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class OnceTaskTriggerJobContext {

    private OnceTaskTriggerContext context;

    private TriggerJobDO job;

    public long successCount;

    public long failCount;

    public long totalCount;

    private Object hintManager;

    public void reduceMonitor() {
        context.successCount.addAndGet(successCount);
        context.failCount.addAndGet(failCount);
        context.totalCount.addAndGet(totalCount);
    }
}