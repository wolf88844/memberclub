/**
 * @(#)SkuAndRestrictInfo.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.quota.extension;

import com.memberclub.domain.dataobject.sku.restrict.SkuRestrictInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuAndRestrictInfo {
    long skuId;

    int buyCount;

    SkuRestrictInfo restrictInfo;
}