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
import com.memberclub.infrastructure.order.facade.RefundOrderRequestDTO;
import com.memberclub.infrastructure.order.facade.RefundOrderResponseDTO;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
public class MockCommonOrderFacadeSPI implements CommonOrderFacadeSPI {

    public static final Logger LOG = LoggerFactory.getLogger(MockCommonOrderFacadeSPI.class);


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

    @Override
    public RefundOrderResponseDTO refund(RefundOrderRequestDTO request) {
        LOG.info("订单收到退款请求:{}", request);
        RefundOrderResponseDTO dto = new RefundOrderResponseDTO();
        dto.setSuccess(true);
        dto.setOrderRefundId(RandomUtils.nextLong() + "");
        return dto;
    }
}