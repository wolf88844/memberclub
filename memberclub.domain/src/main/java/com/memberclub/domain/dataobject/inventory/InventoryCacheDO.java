/**
 * @(#)InventoryCacheDO.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.inventory;

import com.memberclub.domain.entity.inventory.Inventory;
import lombok.Data;

import java.io.Serializable;

/**
 * author: 掘金五阳
 */
@Data
public class InventoryCacheDO implements Serializable {

    public Long targetId;

    public int targetType;

    public String subKey;

    public long totalCount;

    public long saleCount;

    public long version;

    public String getKey() {
        return Inventory.buildInventoryKey(targetType, targetId, subKey);
    }
}