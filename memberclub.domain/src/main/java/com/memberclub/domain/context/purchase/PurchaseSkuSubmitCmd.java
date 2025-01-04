/**
 * @(#)PurchaseSkuSubmitInfo.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseSkuSubmitCmd {

    private long skuId;

    private int buyCount;
    
}