/**
 * @(#)OnceTaskTriggerContext.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.oncetask.trigger;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.oncetask.common.TaskTypeEnum;
import lombok.Data;

import java.util.List;
import java.util.Set;

/**
 * author: 掘金五阳
 */
@Data
public class OnceTaskTriggerContext {

    private BizTypeEnum bizType;

    private Set<Long> userIds;

    private List<OnceTaskStatusEnum> status;

    private TaskTypeEnum taskType;

    private long now;

    private Long minTriggerStime;

    private Long maxTriggerStime;

    private List<TriggerJobDO> jobs;
}