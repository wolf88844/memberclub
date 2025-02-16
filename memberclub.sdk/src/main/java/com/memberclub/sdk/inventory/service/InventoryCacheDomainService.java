/**
 * @(#)InventoryCacheDomainService.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.service;

import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.dataobject.inventory.InventoryCacheCmd;
import com.memberclub.domain.dataobject.inventory.InventoryCacheDO;
import com.memberclub.infrastructure.cache.CacheEnum;
import com.memberclub.infrastructure.cache.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class InventoryCacheDomainService {

    @Autowired
    private CacheService cacheService;

    @Retryable(throwException = false)
    public void syncCaches(InventoryCacheCmd cmd) {
        for (InventoryCacheDO cache : cmd.getCaches()) {
            cacheService.put(CacheEnum.inventory, cache.getKey(), cache);
            CommonLog.info("库存同步到缓存成功 {}", cache);
        }
    }
}