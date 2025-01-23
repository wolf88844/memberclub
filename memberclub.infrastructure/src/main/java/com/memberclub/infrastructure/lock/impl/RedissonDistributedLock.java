/**
 * @(#)RedissonDistributedLock.java, 一月 17, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock.impl;

import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.lock.DistributeLock;
import lombok.SneakyThrows;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.lock", havingValue = "redisson")
@Service
public class RedissonDistributedLock implements DistributeLock {

    public static final Logger LOG = LoggerFactory.getLogger(RedissonDistributedLock.class);


    @Autowired
    private RedissonClient redissonClient;


    @Override
    @SneakyThrows
    public boolean lock(String key, Long value, int timeSeconds) {
        if (ApplicationContextUtils.isTest()) {
            LOG.info("redisson收到加锁请求 key:{}, value:{}, timeSeconds:{}", key, value, timeSeconds);
        }

        RLock lock = redissonClient.getLock(key);

        return lock.tryLockAsync(100, timeSeconds * 1000, TimeUnit.MILLISECONDS, value)
                .get();
    }

    @SneakyThrows
    @Override
    public boolean unlock(String key, Long value) {
        if (ApplicationContextUtils.isTest()) {
            LOG.info("redisson收到解锁请求 key:{}, value:{}", key, value);
        }
        RLock lock = redissonClient.getLock(key);

        lock.unlockAsync(value).get(100, TimeUnit.MILLISECONDS);
        return true;
    }
}