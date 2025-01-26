/**
 * @(#)PreFinanceRecordDO.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.prefinance;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.prefinance.common.PreFinanceRecordStatusEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceRecordDO {
    private Long id;

    private BizTypeEnum bizType;

    private long userId;

    private String tradeId;

    private String subOrderId;

    private int financeProductType;

    private Long preFinanceRecordId;

    private PreFinanceRecordStatusEnum status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}