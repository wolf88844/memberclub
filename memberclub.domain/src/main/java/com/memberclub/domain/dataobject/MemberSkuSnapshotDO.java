/**
 * @(#)MemberSkuDO.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject;

import lombok.Data;

/**
 * @author yuhaiqiang
 */
@Data
public class MemberSkuSnapshotDO {

    private int bizType;

    private long skuId;

    private String displayName;

    private String displayDesc;

    private String internalName;

    private String internalDesc;


    private SkuPerformConfigDO performConfig;
}