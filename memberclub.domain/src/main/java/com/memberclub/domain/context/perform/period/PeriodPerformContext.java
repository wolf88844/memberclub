/**
 * @(#)PeriodPerformContext.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.period;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PeriodPerformContext {

    private BizTypeEnum bizType;

    private long userId;

    private String taskToken;

    private String tradeId;

    private long skuId;

    private OnceTaskDO task;

    private Long lockValue;

    private PerformTaskContentDO content;
}