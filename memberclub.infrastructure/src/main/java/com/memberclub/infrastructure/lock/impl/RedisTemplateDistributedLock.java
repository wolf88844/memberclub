/**
 * @(#)RedisTemplateDistributedLock.java, 一月 22, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock.impl;

import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.lock.DistributeLock;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.lock", havingValue = "redis", matchIfMissing = true)
@Service
public class RedisTemplateDistributedLock implements DistributeLock {


    public static final Logger LOG = LoggerFactory.getLogger(RedisTemplateDistributedLock.class);

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public boolean lock(String key, Long value, int timeSeconds) {
        if (ApplicationContextUtils.isTest()) {
            LOG.info("redis收到加锁请求 key:{}, value:{}, timeSeconds:{}", key, value, timeSeconds);
        }
        boolean succ = getLock(key, String.valueOf(value), timeSeconds);
        if (succ) {
            LOG.info("加锁成功 key:{} value:{}", key, value);
        } else {
            LOG.error("加锁失败 key:{} value:{}", key, value);
        }
        return succ;
    }

    @Override
    public boolean unlock(String key, Long value) {
        if (ApplicationContextUtils.isTest()) {
            LOG.info("redis收到解锁请求 key:{}, value:{}", key, value);
        }
        boolean succ = releaseLock(key, String.valueOf(value));
        if (succ) {
            LOG.info("解锁成功 key:{} value:{}", key, value);
        } else {
            LOG.error("解锁失败需要重试 key:{} value:{}", key, value);
        }
        return succ;
    }

    private static final Long SUCCESS = 1L;

    /**
     * 获取锁
     *
     * @param lockKey
     * @param value
     * @param expireTime 锁有效时间 单位-秒
     * @return
     */
    public boolean getLock(String lockKey, String value, int expireTime) {
        boolean ret = false;
        try {
            //加锁命令，使用SETNX命令加锁，加锁成功返回1
            String script = "if redis.call('setNx',KEYS[1],ARGV[1]) == 1 then if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('expire',KEYS[1],ARGV[2]) else return 0 end else return 0 end";

            RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

            Long result = redisTemplate.execute(redisScript, new StringRedisSerializer(),
                    new GenericToStringSerializer<>(Long.class), Collections.singletonList(lockKey), value, String.valueOf(expireTime));

            //判断加锁成功
            if (SUCCESS.equals(result)) {
                return true;
            }

        } catch (Exception e) {
            LOG.error("加锁异常 lockKey:{}, value:{}, expiredTime:{}", lockKey, value, expireTime, e);
            String redisValue = redisTemplate.opsForValue().get(lockKey);
            if (redisValue != null
                    && StringUtils.equals(redisValue, value)) {
                LOG.info("再次查询实际加锁成功lockKey:{}, value:{}, expiredTime:{}", lockKey, value, expireTime);
                return true;
            }
        }
        return ret;
    }

    /**
     * 释放锁
     *
     * @param lockKey
     * @param value
     * @return
     */
    public boolean releaseLock(String lockKey, String value) {

        //解锁命令，解锁成功返回1
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";

        RedisScript<Long> redisScript = new DefaultRedisScript<>(script, Long.class);

        Long result = redisTemplate.execute(redisScript, new StringRedisSerializer(),
                new GenericToStringSerializer<>(Long.class), Collections.singletonList(lockKey), value);

        //判断解锁成功
        if (SUCCESS.equals(result)) {
            return true;
        }
        String redisValue = redisTemplate.opsForValue().get(lockKey);
        if (redisValue == null) {
            LOG.info("再次查询实际解锁成功lockKey:{}, value:{}", lockKey, value);
            return true;
        }

        return false;
    }
}