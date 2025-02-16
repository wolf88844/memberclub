/**
 * @(#)InventoryBizService.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.service;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.inventory.InventoryOpCmd;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.context.inventory.InventoryOpResponse;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import com.memberclub.domain.exception.MemberException;
import com.memberclub.sdk.inventory.extension.InventoryExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class InventoryBizService {
    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private InventoryDomainService inventoryDomainService;

    public void validate(InventoryOpCmd cmd) {
        cmd.setTargetType(InventoryTargetTypeEnum.SKU);
        InventoryOpContext context = new InventoryOpContext();
        context.setCmd(cmd);
        context.setTargetType(cmd.getTargetType());

        extensionManager.getExtension(BizScene.of(cmd.getBizType()), InventoryExtension.class).validate(context);
    }


    public InventoryOpResponse operateSkuInventory(InventoryOpCmd cmd) {
        cmd.setTargetType(InventoryTargetTypeEnum.SKU);
        InventoryOpContext context = new InventoryOpContext();
        context.setCmd(cmd);
        context.setTargetType(cmd.getTargetType());

        InventoryOpResponse response = new InventoryOpResponse();
        try {
            extensionManager.getExtension(BizScene.of(cmd.getBizType()), InventoryExtension.class).operate(context);
            response.setSuccess(true);
        } catch (MemberException e) {
            CommonLog.warn("库存操作异常 cmd:{}", cmd, e);
            response.setSuccess(e.getCode().isSuccess());
            response.setNeedRetry(e.getCode().isNeedRetry());
            response.setE(e);
        } catch (Exception e) {
            response.setSuccess(false);
            response.setNeedRetry(true);//保险起见重试几次(内部会幂等)
            response.setE(e);
        }
        return response;
    }
}