/**
 * @(#)NoneDistributedLock.java, 十二月 26, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock.impl;

import com.memberclub.infrastructure.lock.DistributeLock;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.lock", havingValue = "local")
@Service
public class LocalDistributedLock implements DistributeLock {

    private ConcurrentMap<String, Long> lockMap = new ConcurrentHashMap<>();

    @Override
    public boolean lock(String key, Long value, int timeSeconds) {
        Long oldValue = lockMap.putIfAbsent(key, value);

        return oldValue == null;
    }

    @Override
    public void unlock(String key, Long value) {
        lockMap.remove(key, value);
    }
}