/**
 * @(#)PurchaseOperateInventoryFlow.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.context.inventory.InventoryOpCmd;
import com.memberclub.domain.context.inventory.InventoryOpResponse;
import com.memberclub.domain.context.inventory.InventoryOpTypeEnum;
import com.memberclub.domain.context.inventory.InventorySkuOpDO;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.sdk.inventory.service.InventoryBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PurchaseOperateInventoryFlow extends FlowNode<PurchaseSubmitContext> {

    @Autowired
    private InventoryBizService inventoryBizService;

    @Autowired
    private PurchaseOperateInventoryFlow purchaseOperateInventoryFlow;

    @Override
    public void process(PurchaseSubmitContext context) {
        InventoryOpCmd inventoryOpCmd = new InventoryOpCmd();
        inventoryOpCmd.setBizType(context.getBizType());
        inventoryOpCmd.setOperateKey(context.getMemberOrder().getTradeId());
        inventoryOpCmd.setOpType(InventoryOpTypeEnum.DECREMENT);
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


        InventoryOpResponse response = inventoryBizService.operateSkuInventory(inventoryOpCmd);
        if (response.isNeedRetry()) {
            CommonLog.error("库存扣减操作需要重试 response:{} cmd:{}", response, inventoryOpCmd);
            purchaseOperateInventoryFlow.retryOperate(inventoryOpCmd);
        }

        if (!response.isSuccess()) {
            throw ResultCode.COMMON_ORDER_SUBMIT_ERROR.newException("扣减库存失败", response.getE());
        }
    }

    @Override
    public void rollback(PurchaseSubmitContext context, Exception e) {
        InventoryOpCmd inventoryOpCmd = new InventoryOpCmd();
        inventoryOpCmd.setBizType(context.getBizType());
        inventoryOpCmd.setOperateKey(context.getMemberOrder().getTradeId());
        inventoryOpCmd.setOpType(InventoryOpTypeEnum.ROLLBACK);
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


        InventoryOpResponse response = inventoryBizService.operateSkuInventory(inventoryOpCmd);
        if (response.isNeedRetry()) {
            CommonLog.error("库存回补操作需要重试 response:{} cmd:{}", response, inventoryOpCmd);
            purchaseOperateInventoryFlow.retryOperate(inventoryOpCmd);
        }

        if (!response.isSuccess()) {
            throw ResultCode.COMMON_ORDER_SUBMIT_ERROR.newException("回补库存失败", response.getE());
        }
    }

    @Retryable(throwException = false)
    public void retryOperate(InventoryOpCmd cmd) {
        inventoryBizService.operateSkuInventory(cmd);
    }
}