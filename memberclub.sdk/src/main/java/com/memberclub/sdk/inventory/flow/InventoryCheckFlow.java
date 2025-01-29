/**
 * @(#)InventoryCheckFlow.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.flow;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.flow.SkipException;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.sdk.inventory.extension.InventoryExtension;
import com.memberclub.sdk.inventory.service.InventoryDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class InventoryCheckFlow extends FlowNode<InventoryOpContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void process(InventoryOpContext context) {
        //查询商品
        inventoryDomainService.querySkuInventorys(context);

        extensionManager.getExtension(BizScene.of(context.getCmd().getBizType()),
                InventoryExtension.class).buildSubKey(context);

        boolean enableOperateInventory = context.getSkuId2InventoryInfo().entrySet().stream()
                .anyMatch(entry -> entry.getValue().isEnable());
        if (!enableOperateInventory) {
            throw new SkipException("无需扣减库存,跳过");
        }
    }
}