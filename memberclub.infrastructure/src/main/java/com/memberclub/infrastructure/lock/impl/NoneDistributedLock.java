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

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.lock", havingValue = "none")
@Service
public class NoneDistributedLock implements DistributeLock {

    @Override
    public boolean lock(String key, String value, int timeSeconds) {
        return true;
    }

    @Override
    public boolean unlock(String key, String value) {
        return true;
    }
}