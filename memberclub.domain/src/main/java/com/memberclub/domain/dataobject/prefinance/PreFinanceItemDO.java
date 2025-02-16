/**
 * @(#)PreFinanceItemDO.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.prefinance;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.prefinance.common.PreFinanceItemStatusEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceItemDO {
    private Long id;

    private BizTypeEnum bizType;

    private long userId;

    private String tradeId;

    private String subOrderId;

    private Long preFinanceRecordId;

    private Long preFinanceItemId;

    private String preFinanceItemCode;

    private int financeAssetType;

    private int periodIndex;

    private int periodCount;

    private PreFinanceItemExtra extra;

    private PreFinanceItemStatusEnum status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}