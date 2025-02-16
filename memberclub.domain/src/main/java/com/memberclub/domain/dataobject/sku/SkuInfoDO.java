/**
 * @(#)SkuBuyDetail.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import com.memberclub.domain.dataobject.sku.restrict.SkuRestrictInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuInfoDO {

    private long skuId;

    private int buyCount;

    private int bizType;

    private SkuViewInfo viewInfo = new SkuViewInfo();

    private SkuFinanceInfo financeInfo = new SkuFinanceInfo();

    private SkuSaleInfo saleInfo = new SkuSaleInfo();

    private SkuPerformConfigDO performConfig = new SkuPerformConfigDO();

    private SkuInventoryInfo inventoryInfo = new SkuInventoryInfo();

    private SkuRestrictInfo restrictInfo = new SkuRestrictInfo();

    private SkuExtra extra = new SkuExtra();

    private long utime;

    private long ctime;
}