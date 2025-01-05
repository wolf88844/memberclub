/**
 * @(#)MockCommonOrderFacadeSPI.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.impl;

import com.memberclub.domain.dataobject.purchase.facade.SkuBuyResultDO;
import com.memberclub.infrastructure.order.context.SkuBuyInfoDTO;
import com.memberclub.infrastructure.order.context.SubmitOrderRequestDTO;
import com.memberclub.infrastructure.order.context.SubmitOrderResponseDTO;
import com.memberclub.infrastructure.order.facade.CommonOrderFacadeSPI;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
public class MockCommonOrderFacadeSPI implements CommonOrderFacadeSPI {


    @Override
    public SubmitOrderResponseDTO submit(SubmitOrderRequestDTO request) {
        SubmitOrderResponseDTO result = new SubmitOrderResponseDTO();
        result.setSuccess(true);
        result.setOrderId(RandomUtils.nextLong() + "");

        List<SkuBuyResultDO> results = Lists.newArrayList();

        for (SkuBuyInfoDTO sku : request.getSkus()) {
            SkuBuyResultDO skuBuyResultDO = new SkuBuyResultDO();
            skuBuyResultDO.setActPriceFen((sku.getSalePrice() - 100) * sku.getBuyCount());
            skuBuyResultDO.setSkuId(sku.getSkuId());
            skuBuyResultDO.setBuyCount(sku.getBuyCount());
            results.add(skuBuyResultDO);
        }
        result.setSkuBuyResults(results);
        result.setActPriceFen(results.stream().collect(Collectors.summingInt(SkuBuyResultDO::getActPriceFen)));

        return result;
    }
}