/**
 * @(#)SkuRestrictItem.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku.restrict;

import com.memberclub.domain.dataobject.sku.UserTypeEnum;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SkuRestrictItem {

    private RestrictPeriodType periodType;

    private int periodCount;

    private RestrictItemType itemType;

    private List<UserTypeEnum> userTypes;

    private Long total;

}