/**
 * @(#)OnceTaskDO.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.task;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.oncetask.common.TaskTypeEnum;
import com.memberclub.domain.context.oncetask.execute.OnceTaskExecuteContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class OnceTaskDO {

    private BizTypeEnum bizType;

    private long userId;

    private String taskGroupId;

    private String tradeId;

    private String taskToken;

    private long stime;

    private long etime;

    private String taskContentClassName;

    private TaskTypeEnum taskType;

    private TaskContentDO content;

    private OnceTaskStatusEnum status;

    private long utime;

    private long ctime;

    public void onCancel(ReversePerformContext reversePerformContext,
                         SubOrderReversePerformContext context) {
        setUtime(System.currentTimeMillis());
        setStatus(OnceTaskStatusEnum.CANCEL);
    }

    public void onStartExecute(OnceTaskExecuteContext context) {
        setStatus(OnceTaskStatusEnum.PROCESSING);
        setUtime(System.currentTimeMillis());
    }

    public void onExecuteSuccess(OnceTaskExecuteContext context) {
        setStatus(OnceTaskStatusEnum.SUCCESS);
        setUtime(System.currentTimeMillis());
    }

    public void onExecuteFail(OnceTaskExecuteContext context) {
        setStatus(OnceTaskStatusEnum.FAIL);
        setUtime(System.currentTimeMillis());
    }
}