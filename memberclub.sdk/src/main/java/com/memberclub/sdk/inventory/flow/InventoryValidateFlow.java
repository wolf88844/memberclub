/**
 * @(#)InventoryCheckFlow.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.sdk.inventory.service.InventoryDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class InventoryValidateFlow extends FlowNode<InventoryOpContext> {

    @Autowired
    private InventoryDomainService inventoryDomainService;

    @Override
    public void process(InventoryOpContext context) {
        boolean isEnough = inventoryDomainService.isEnough(context);
        if (!isEnough) {
            throw ResultCode.INVENTORY_LACKING.newException("库存不足");
        }
        CommonLog.info("库存前置校验通过,库存充足 content:{}", context);
    }
}