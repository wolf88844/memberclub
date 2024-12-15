/**
 * @(#)SkuBuyDetail.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SkuBuyDetailDO {

    private long skuId;

    private int buyCount;

    private MemberSkuSnapshotDO skuSnapshot;
}