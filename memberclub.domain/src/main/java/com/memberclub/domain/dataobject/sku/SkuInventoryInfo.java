/**
 * @(#)SkuInventoryInfo.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuInventoryInfo {

    private boolean enable;

    /**
     * todoy 如果存在多个库存呢?
     *
     * @see
     */
    private int type;

    private Long total;
}