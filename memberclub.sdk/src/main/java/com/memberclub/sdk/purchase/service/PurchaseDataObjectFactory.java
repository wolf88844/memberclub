/**
 * @(#)PurchaseDataObjectFactory.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.service;

import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.context.inventory.InventoryOpCmd;
import com.memberclub.domain.context.inventory.InventoryOpTypeEnum;
import com.memberclub.domain.context.inventory.InventorySkuOpDO;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PurchaseDataObjectFactory {

    public InventoryOpCmd toInventoryOpCmd(PurchaseSubmitContext context, InventoryOpTypeEnum opType) {
        InventoryOpCmd inventoryOpCmd = new InventoryOpCmd();
        inventoryOpCmd.setBizType(context.getBizType());
        inventoryOpCmd.setOperateKey(context.getMemberOrder().getTradeId());
        inventoryOpCmd.setOpType(opType);
        inventoryOpCmd.setSkus(CollectionUtilEx.mapToList(context.getSubmitCmd().getSkus(), sku -> {
                    InventorySkuOpDO skuOpDO = new InventorySkuOpDO();
                    skuOpDO.setSkuId(sku.getSkuId());
                    skuOpDO.setCount(sku.getBuyCount());
                    return skuOpDO;
                }
        ));
        inventoryOpCmd.setSource(context.getSubmitCmd().getSource().getCode());
        inventoryOpCmd.setTargetType(InventoryTargetTypeEnum.SKU);
        inventoryOpCmd.setUserId(context.getUserId());
        return inventoryOpCmd;
    }

    public InventoryOpCmd toInventoryOpCmdOnValidate(PurchaseSubmitContext context, InventoryOpTypeEnum opType) {
        InventoryOpCmd inventoryOpCmd = new InventoryOpCmd();
        inventoryOpCmd.setBizType(context.getBizType());
        
        inventoryOpCmd.setOpType(opType);
        inventoryOpCmd.setSkus(CollectionUtilEx.mapToList(context.getSubmitCmd().getSkus(), sku -> {
                    InventorySkuOpDO skuOpDO = new InventorySkuOpDO();
                    skuOpDO.setSkuId(sku.getSkuId());
                    skuOpDO.setCount(sku.getBuyCount());
                    return skuOpDO;
                }
        ));
        inventoryOpCmd.setSource(context.getSubmitCmd().getSource().getCode());
        inventoryOpCmd.setTargetType(InventoryTargetTypeEnum.SKU);
        inventoryOpCmd.setUserId(context.getUserId());
        return inventoryOpCmd;
    }
}