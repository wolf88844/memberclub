/**
 * @(#)PreFinanceEventDetail.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceEventDetail {

    /**
     * 实际数量
     */
    public List<FinanceAssetDO> assets;

    /**
     * 期望数量
     */
    private Integer assetNum;

    /**
     * 结算资产类型
     */
    private int financeAssetType;

    /**
     * 资产编码
     */
    private String assetBatchCode;

    private long stime;

    private long etime;
}