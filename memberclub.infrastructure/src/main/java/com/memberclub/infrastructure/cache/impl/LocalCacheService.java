/**
 * @(#)LocalCacheService.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.cache.impl;

import com.memberclub.domain.dataobject.inventory.InventoryCacheDO;
import com.memberclub.infrastructure.cache.CacheEnum;
import com.memberclub.infrastructure.cache.CacheService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.cache", havingValue = "local", matchIfMissing = false)
@Service
public class LocalCacheService implements CacheService {


    private static ConcurrentMap<String, InventoryCacheDO> inventoryMap = new ConcurrentHashMap();

    @Override
    public <K, V> V put(CacheEnum cacheEnum, K k, V v) {
        if (cacheEnum == CacheEnum.inventory) {
            return (V) inventoryMap.put((String) k, (InventoryCacheDO) v);
        }
        return null;
    }

    @Override
    public <K, V> V get(CacheEnum cacheEnum, K k) {
        if (cacheEnum == CacheEnum.inventory) {
            return (V) inventoryMap.get((String) k);
        }
        return null;
    }
}