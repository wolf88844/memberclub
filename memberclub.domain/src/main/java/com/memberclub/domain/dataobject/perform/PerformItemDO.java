/**
 * @(#)PerformItemDO.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PerformItemDO {

    private long skuId;

    private long rightId;

    private int rightType;

    private String batchCode;

    private int assetCount;

    private int phase = 1;

    private int cycle;

    private int buyIndex = 1;

    /***
     * 0 发放
     * 1 激活
     */
    private int grantType;

    private int periodCount;

    private int periodType;

    private long stime;

    private long etime;
}