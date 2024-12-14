/**
 * @(#)MemberPerformItem.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity;

import lombok.Data;

/**
 * @author 掘金五阳
 */
@Data
public class MemberPerformItem {
    private int bizType;

    private long userId;

    private String tradeId;

    private long skuId;

    private long rightId;

    private int rightType;

    private int batchCode;

    private int phase;

    private int cycle;

    /***
     * 0 发放
     * 1 激活
     */
    private int grantType;

    private String extra;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}