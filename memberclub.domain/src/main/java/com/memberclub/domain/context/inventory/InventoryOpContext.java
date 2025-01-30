/**
 * @(#)InventoryOpContext.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.inventory;

import com.memberclub.domain.dataobject.sku.SkuInventoryInfo;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class InventoryOpContext {

    private InventoryTargetTypeEnum targetType;

    private InventoryOpCmd cmd;

    private Map<Long, SkuInventoryInfo> skuId2InventoryInfo;

    public boolean isOperatable() {
        return !CollectionUtils.isEmpty(cmd.getSkus());
    }

}