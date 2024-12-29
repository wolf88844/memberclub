/**
 * @(#)OnceTaskDO.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.task;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.TaskTypeEnum;
import com.memberclub.domain.common.status.OnceTaskStatusEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class OnceTaskDO {

    private BizTypeEnum bizType;

    private long userId;

    private String taskToken;

    private long stime;

    private long etime;

    private String taskContentClassName;

    private TaskTypeEnum taskType;
    
    private TaskContentDO content;

    private OnceTaskStatusEnum status;
    private long utime;

    private long ctime;
}