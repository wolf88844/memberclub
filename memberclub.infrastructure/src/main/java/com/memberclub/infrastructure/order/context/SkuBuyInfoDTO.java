/**
 * @(#)SkuBuyInfoDTO.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.order.context;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuBuyInfoDTO {

    private long skuId;

    private int buyCount;

    private int salePrice;
}