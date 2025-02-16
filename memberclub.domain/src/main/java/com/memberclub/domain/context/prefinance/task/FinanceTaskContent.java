/**
 * @(#)FinanceTaskContent.java, 一月 28, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance.task;

import com.memberclub.domain.dataobject.task.TaskContentDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class FinanceTaskContent extends TaskContentDO {

    private String tradeId;

    private String subTradeId;

    private String itemToken;

    private Integer phase;

    private Long skuId;

    private Long stime;

    private Long etime;
}