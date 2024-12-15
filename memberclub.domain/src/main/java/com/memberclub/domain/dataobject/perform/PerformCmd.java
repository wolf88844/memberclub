/**
 * @(#)PerformCmd.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PerformCmd {

    private BizTypeEnum bizType;

    private long userId;

    private String orderId;

    private OrderSystemTypeEnum orderSystemType;

    private String actPriceFen;

    private String originPriceFen;

    private int retryTimes;

    private String lockValue;
}