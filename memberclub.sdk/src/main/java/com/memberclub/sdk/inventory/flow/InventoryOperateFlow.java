/**
 * @(#)InventoryOperateFlow.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.context.inventory.InventoryOpTypeEnum;
import com.memberclub.sdk.inventory.service.InventoryDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class InventoryOperateFlow extends FlowNode<InventoryOpContext> {
    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void process(InventoryOpContext context) {
        if (context.getCmd().getOpType() == InventoryOpTypeEnum.DECREMENT) {
            inventoryDomainService.decrement(context);
        } else {
            inventoryDomainService.rollback(context);
        }
    }


}