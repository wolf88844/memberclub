/**
 * @(#)MemberSkuDataObjectFactory.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.dataobject.sku.SkuFinanceInfo;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuInventoryInfo;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.SkuRestrictInfo;
import com.memberclub.domain.dataobject.sku.SkuSaleInfo;
import com.memberclub.domain.dataobject.sku.SkuViewInfo;
import com.memberclub.domain.entity.sku.MemberSku;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberSkuDataObjectFactory {

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