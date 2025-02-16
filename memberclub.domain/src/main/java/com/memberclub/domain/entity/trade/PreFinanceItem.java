/**
 * @(#)PreFinanceItem.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity.trade;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private long userId;

    private String tradeId;

    private String subOrderId;

    private Long preFinanceRecordId;

    private Long preFinanceItemId;

    private String preFinanceItemCode;

    private int financeAssetType;

    private int periodIndex;

    private int periodCount;

    private String extra;

    private int status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}