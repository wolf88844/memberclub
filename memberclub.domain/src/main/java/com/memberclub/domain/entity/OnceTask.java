/**
 * @(#)OnceTask.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class OnceTask {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private int taskType;

    private long userId;
    
    private String taskGroupId;

    private String taskToken;

    private long stime;

    private long etime;

    private String taskContentClassName;

    private int status;

    private String content;

    private long utime;

    private long ctime;
}