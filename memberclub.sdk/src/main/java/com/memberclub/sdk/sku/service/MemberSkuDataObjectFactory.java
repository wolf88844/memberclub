/**
 * @(#)MemberSkuDataObjectFactory.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.context.inventory.InventoryOpTypeEnum;
import com.memberclub.domain.context.inventory.InventorySkuOpDO;
import com.memberclub.domain.context.inventory.InventoryStatusEnum;
import com.memberclub.domain.dataobject.sku.InventoryTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuFinanceInfo;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuInventoryInfo;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.SkuRestrictInfo;
import com.memberclub.domain.dataobject.sku.SkuSaleInfo;
import com.memberclub.domain.dataobject.sku.SkuViewInfo;
import com.memberclub.domain.entity.inventory.Inventory;
import com.memberclub.domain.entity.inventory.InventoryRecord;
import com.memberclub.domain.entity.inventory.InventoryTargetTypeEnum;
import com.memberclub.domain.entity.sku.MemberSku;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberSkuDataObjectFactory {


    public Inventory buildInventoryFromSku(SkuInfoDO info) {
        Inventory inventory = new Inventory();
        inventory.setBizType(info.getBizType());
        inventory.setCtime(TimeUtil.now());
        inventory.setUtime(TimeUtil.now());
        inventory.setVersion(0);
        //inventory.setEtime(info.getSaleInfo());
        //inventory.setStime();
        inventory.setStatus(InventoryStatusEnum.ACTIVTE.getCode());
        inventory.setSaleCount(0);
        inventory.setTotalCount(info.getInventoryInfo().getTotal());
        inventory.setSubKey(Inventory.buildSubKey(InventoryTypeEnum.findByCode(info.getInventoryInfo().getType())));
        inventory.setTargetId(info.getSkuId());
        inventory.setTargetType(InventoryTargetTypeEnum.SKU.getCode());
        return inventory;
    }

    public InventoryRecord buildRecord(InventoryOpContext context, InventoryOpTypeEnum opType, InventorySkuOpDO skuOpDO) {
        InventoryRecord record = new InventoryRecord();
        record.setBizType(context.getCmd().getBizType().getCode());
        record.setCtime(TimeUtil.now());
        record.setUtime(TimeUtil.now());
        record.setOpCount(skuOpDO.getCount());
        record.setOperateKey(context.getCmd().getOperateKey());
        record.setOpType(opType.getCode());
        record.setSubKey(skuOpDO.getSubKey());
        record.setTargetId(skuOpDO.getSkuId());
        record.setTargetType(context.getTargetType().getCode());
        record.setUserId(context.getCmd().getUserId());
        record.setInventoryKey(Inventory.buildInventoryKey(record.getTargetType(), record.getTargetId(), record.getSubKey()));
        return record;
    }


    public MemberSku toSku(SkuInfoDO skuInfoDO) {
        MemberSku sku = new MemberSku();
        sku.setId(skuInfoDO.getSkuId());
        sku.setBizType(skuInfoDO.getBizType());
        sku.setCtime(skuInfoDO.getCtime());
        sku.setUtime(skuInfoDO.getUtime());
        sku.setExtra(JsonUtils.toJson(skuInfoDO.getExtra()));
        sku.setFinanceInfo(JsonUtils.toJson(skuInfoDO.getFinanceInfo()));

        sku.setInventoryInfo(JsonUtils.toJson(skuInfoDO.getInventoryInfo()));
        sku.setPerformanceInfo(JsonUtils.toJson(skuInfoDO.getPerformConfig()));
        sku.setRestrictInfo(JsonUtils.toJson(skuInfoDO.getRestrictInfo()));
        sku.setSaleInfo(JsonUtils.toJson(skuInfoDO.getSaleInfo()));
        sku.setViewInfo(JsonUtils.toJson(skuInfoDO.getViewInfo()));
        sku.setStatus(0);
        return sku;
    }

    public SkuInfoDO toSkuInfoDO(MemberSku sku) {
        SkuInfoDO skuInfoDO = new SkuInfoDO();
        skuInfoDO.setSkuId(sku.getId());
        skuInfoDO.setBizType(sku.getBizType());
        skuInfoDO.setFinanceInfo(JsonUtils.fromJson(sku.getFinanceInfo(), SkuFinanceInfo.class));
        skuInfoDO.setInventoryInfo(JsonUtils.fromJson(sku.getInventoryInfo(), SkuInventoryInfo.class));
        skuInfoDO.setPerformConfig(JsonUtils.fromJson(sku.getPerformanceInfo(), SkuPerformConfigDO.class));
        skuInfoDO.setRestrictInfo(JsonUtils.fromJson(sku.getRestrictInfo(), SkuRestrictInfo.class));
        skuInfoDO.setSaleInfo(JsonUtils.fromJson(sku.getSaleInfo(), SkuSaleInfo.class));
        skuInfoDO.setViewInfo(JsonUtils.fromJson(sku.getViewInfo(), SkuViewInfo.class));
        return skuInfoDO;
    }
}