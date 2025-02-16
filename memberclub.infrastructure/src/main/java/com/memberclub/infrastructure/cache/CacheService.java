/**
 * @(#)CacheService.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.cache;

/**
 * @author wuyang
 */
public interface CacheService {

    public <K, V> V put(CacheEnum cacheEnum, K k, V v);

    public <K, V> V del(CacheEnum cacheEnum, K k);

    public <K, V> V get(CacheEnum cacheEnum, K k);
}