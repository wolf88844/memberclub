/**
 * @(#)DefaultInventoryExtension.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.extension.iml;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.context.inventory.InventorySkuOpDO;
import com.memberclub.domain.dataobject.sku.InventoryTypeEnum;
import com.memberclub.domain.entity.inventory.Inventory;
import com.memberclub.sdk.inventory.extension.InventoryExtension;
import com.memberclub.sdk.inventory.flow.InventoryCheckFlow;
import com.memberclub.sdk.inventory.flow.InventoryOperateFlow;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "库存扩展点", bizScenes = {@Route(
        bizType = BizTypeEnum.DEFAULT
)})
public class DefaultInventoryExtension implements InventoryExtension {

    private FlowChain<InventoryOpContext> inventoryOpContextFlowChain = null;


    @PostConstruct
    public void init() {
        inventoryOpContextFlowChain = FlowChain.newChain(InventoryOpContext.class)
                .addNode(InventoryCheckFlow.class)
                .addNode(InventoryOperateFlow.class)
        ;
    }


    @Override
    public void operate(InventoryOpContext context) {
        inventoryOpContextFlowChain.execute(context);
    }

    @Override
    public void buildSubKey(InventoryOpContext context) {
        for (InventorySkuOpDO sku : context.getCmd().getSkus()) {
            //总量库存
            sku.setSubKey(Inventory.buildSubKey(InventoryTypeEnum.TOTAL));
        }
    }
}