/**
 * @(#)SkuRestrictItem.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku.restrict;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SkuRestrictItem {

    private RestrictPeriodType periodType;

    private RestrictItemType itemType;
    
    private List<RestrictUserTypeEnum> userTypes;

    private Long total;

}