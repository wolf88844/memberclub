/**
 * @(#)Inventory.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.memberclub.domain.dataobject.inventory.InventoryCacheDO;
import com.memberclub.domain.dataobject.sku.InventoryTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class Inventory {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private Long targetId;

    private int targetType;

    private String subKey;

    private long saleCount;

    private long totalCount;

    private long stime;

    private long etime;

    private int status;

    private long version;

    private long utime;

    private long ctime;

    public static String buildInventoryKey(int targetType, Long targetId, String subKey) {
        return String.format("%s_%s_%s", targetType, targetId, subKey);
    }

    public static String buildSubKey(InventoryTypeEnum type) {
        if (type == InventoryTypeEnum.TOTAL) {
            return "total";
        }
        return "";
    }

    public static InventoryCacheDO toCache(Inventory inventory) {
        InventoryCacheDO cache = new InventoryCacheDO();
        cache.setSaleCount(inventory.getSaleCount());
        cache.setTotalCount(inventory.getTotalCount());
        cache.setVersion(inventory.getVersion());
        cache.setTargetId(inventory.getTargetId());
        cache.setTargetType(inventory.getTargetType());
        cache.setSubKey(inventory.getSubKey());
        return cache;
    }
}