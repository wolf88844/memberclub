/**
 * @(#)MemberSkuDO.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import lombok.Data;

/**
 * @author 掘金五阳
 */
@Data
public class MemberSkuSnapshotDO {

    private int bizType;

    private long skuId;

    private SkuViewInfo display;

    private SkuPerformConfigDO performConfig;
}