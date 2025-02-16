/**
 * @(#)SkuSettleInfo.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import lombok.Data;

/**
 * @see com.memberclub.domain.dataobject.perform.his.SubOrderFinanceInfo
 * author: 掘金五阳
 */
@Data
public class SkuFinanceInfo {

    /**
     * 承包商 Id
     */
    public String contractorId;

    /**
     * 结算价格
     */
    public Integer settlePriceFen;

    /**
     * 周期数量
     */
    public Integer periodCycle;

    /**
     * 结算产品类型
     */
    public Integer financeProductType;
}