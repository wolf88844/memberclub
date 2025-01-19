/**
 * @(#)MockSkuBizService.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service.impl;

import com.google.common.collect.Maps;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.infrastructure.sku.SkuBizService;
import com.memberclub.sdk.sku.service.SkuDomainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.sku", havingValue = "local", matchIfMissing = false)
@Configuration
public class MockSkuBizService implements SkuBizService {

    private Map<Long, SkuInfoDO> map = Maps.newHashMap();

    public static final Logger LOG = LoggerFactory.getLogger(MockSkuBizService.class);


    @Autowired
    private SkuDomainService skuDomainService;

    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void init() {
        List<SkuInfoDO> skus = skuDomainService.queryAllSkus();
        for (SkuInfoDO skuInfoDO : skus) {
            LOG.info("加载商品数据到缓存:{}", skuInfoDO.getSkuId());
            addSku(skuInfoDO.getSkuId(), skuInfoDO);
        }
    }

    public void addSku(long skuId, SkuInfoDO skuInfo) {
        map.put(skuId, skuInfo);
    }

    @Override
    public SkuInfoDO querySku(BizTypeEnum bizType, long skuId) {
        return map.get(skuId);
    }
}