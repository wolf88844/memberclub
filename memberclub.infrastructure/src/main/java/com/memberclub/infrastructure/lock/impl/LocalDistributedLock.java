/**
 * @(#)NoneDistributedLock.java, 十二月 26, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock.impl;

import com.memberclub.common.log.CommonLog;
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

        boolean succ = oldValue == null;
        if (!succ) {
            CommonLog.error("加锁失败,需要上游重试 key:{}, value:{}", key, value);
        } else {
            CommonLog.info("加锁成功 key:{}, value:{}", key, value);
        }
        return succ;
    }

    @Override
    public boolean unlock(String key, Long value) {
        boolean succ = lockMap.remove(key, value);
        if (!succ) {
            if (!lockMap.containsKey(key)) {
                CommonLog.error("未发现锁,无法解锁 key:{}, value:{}", key, value);
                return true;
            }
            CommonLog.error("解锁失败,自动重试 key:{}, value:{}", key, value);
        } else {
            CommonLog.info("解锁成功 key:{}, value:{}", key, value);
        }
        return succ;
    }
}