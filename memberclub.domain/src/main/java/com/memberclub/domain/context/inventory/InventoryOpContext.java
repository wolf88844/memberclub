/**
 * @(#)InventoryOpContext.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.inventory;

import com.memberclub.domain.dataobject.sku.SkuInventoryInfo;
import com.memberclub.domain.entity.inventory.InventoryRecord;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class InventoryOpContext {

    private InventoryTargetTypeEnum targetType;

    private InventoryOpCmd cmd;

    //回补阶段使用历史记录构建要回补的库存
    Map<Long, List<InventoryRecord>> skuId2InventoryRecords;

    //扣减阶段基于商品信息构建要扣减的库存
    private Map<Long, SkuInventoryInfo> skuId2InventoryInfo;

    public boolean isOperatable() {
        return !CollectionUtils.isEmpty(cmd.getSkus());
    }

}