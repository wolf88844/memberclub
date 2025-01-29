/**
 * @(#)InventoryDomainService.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.inventory.InventoryOpCmd;
import com.memberclub.domain.context.inventory.InventoryOpContext;
import com.memberclub.domain.context.inventory.InventoryOpTypeEnum;
import com.memberclub.domain.context.inventory.InventorySkuOpDO;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuInventoryInfo;
import com.memberclub.domain.entity.inventory.Inventory;
import com.memberclub.domain.entity.inventory.InventoryRecord;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.sku.InventoryDao;
import com.memberclub.infrastructure.mybatis.mappers.sku.InventoryRecordDao;
import com.memberclub.infrastructure.sku.SkuBizService;
import com.memberclub.sdk.sku.service.MemberSkuDataObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@DS("skuDataSource")
@Service
public class InventoryDomainService {

    @Autowired
    private InventoryDao inventoryDao;

    @Autowired
    private InventoryRecordDao inventoryRecordDao;

    @Autowired
    private SkuBizService skuBizService;

    @Autowired
    private MemberSkuDataObjectFactory memberSkuDataObjectFactory;

    public void querySkuInventorys(InventoryOpContext context) {
        Map<Long, SkuInventoryInfo> skuId2Inventorys = Maps.newHashMap();
        for (InventorySkuOpDO sku : context.getCmd().getSkus()) {
            SkuInfoDO skuInfoDO = skuBizService.querySku(context.getCmd().getBizType(), sku.getSkuId());
            skuId2Inventorys.put(sku.getSkuId(), skuInfoDO.getInventoryInfo());
        }
        context.setSkuId2InventoryInfo(skuId2Inventorys);
    }

    public List<Inventory> queryInventorys(Long skuId) {
        LambdaQueryWrapper<Inventory> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Inventory::getTargetId, skuId);
        return inventoryDao.selectList(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    public void decrement(InventoryOpContext context) {
        List<InventoryRecord> records = Lists.newArrayList();

        for (InventorySkuOpDO sku : context.getCmd().getSkus()) {
            int cnt = inventoryDao.decrement(context.getTargetType().getCode(),
                    sku.getSkuId(),
                    sku.getSubKey(),
                    sku.getCount(), TimeUtil.now());
            if (cnt < 1) {
                throw ResultCode.INVENTORY_DECREMENT_FAIL.newException();
            }
            InventoryRecord record = memberSkuDataObjectFactory.buildRecord(context, InventoryOpTypeEnum.DECREMENT, sku);
            records.add(record);
        }

        int cnt = inventoryRecordDao.insertIgnoreBatch(records);
        if (cnt < records.size()) {
            throw ResultCode.INVENTORY_DECREMENT_DUPLICATED.newException();
        }

        CommonLog.info("完成扣减库存 context:{}", context);
    }

    @Transactional(rollbackFor = Exception.class)
    public void rollback(InventoryOpContext context) {
        List<InventoryRecord> records = Lists.newArrayList();

        InventoryOpCmd cmd = context.getCmd();

        for (InventorySkuOpDO sku : context.getCmd().getSkus()) {
            InventoryRecord decrementRecord = inventoryRecordDao.queryRecord(cmd.getUserId(),
                    cmd.getOperateKey(),
                    Inventory.buildInventoryKey(cmd.getTargetType().getCode(), sku.getSkuId(), sku.getSubKey()),
                    InventoryOpTypeEnum.DECREMENT.getCode());
            if (decrementRecord == null) {
                throw ResultCode.INVENTORY_ROLLBACK_INVALID.newException();
            }
            int cnt = inventoryDao.rollback(context.getTargetType().getCode(),
                    sku.getSkuId(),
                    sku.getSubKey(),
                    sku.getCount(), TimeUtil.now());
            if (cnt < 1) {
                throw ResultCode.INVENTORY_ROLLBACK_FAIL.newException();
            }
            InventoryRecord record = memberSkuDataObjectFactory.buildRecord(context, InventoryOpTypeEnum.ROLLBACK, sku);
            records.add(record);
        }

        int cnt = inventoryRecordDao.insertIgnoreBatch(records);
        if (cnt < records.size()) {
            throw ResultCode.INVENTORY_DECREMENT_DUPLICATED.newException();
        }
        CommonLog.info("完成回补库存 context:{}", context);
    }
}