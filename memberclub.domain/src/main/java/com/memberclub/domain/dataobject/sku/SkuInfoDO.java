/**
 * @(#)SkuBuyDetail.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuInfoDO {

    private long skuId;

    private int buyCount;

    private int bizType;

    private SkuViewInfo viewInfo;

    private SkuFinanceInfo financeInfo;

    private SkuSaleInfo saleInfo;

    private SkuPerformConfigDO performConfig;

    private SkuInventoryInfo inventoryInfo;

    private SkuRestrictInfo restrictInfo;
}