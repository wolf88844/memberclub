/**
 * @(#)MockSkuBizService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.impl;

import com.google.common.collect.Maps;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.sdk.sku.service.SkuBizService;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * author: 掘金五阳
 */
@Service
public class MockSkuBizService implements SkuBizService {

    private Map<Long, SkuInfoDO> map = Maps.newHashMap();

    public void addSku(long skuId, SkuInfoDO skuInfo) {
        map.put(skuId, skuInfo);
    }

    @Override
    public SkuInfoDO querySku(BizTypeEnum bizType, long skuId) {
        return map.get(skuId);
    }
}