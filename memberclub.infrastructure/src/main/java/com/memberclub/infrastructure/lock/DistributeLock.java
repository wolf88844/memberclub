/**
 * @(#)DistributeLock.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock;

import com.memberclub.common.retry.Retryable;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class DistributeLock {

    @Retryable
    public boolean lock(String key, String value, int timeSeconds) {
        return true;
    }

    @Retryable
    public boolean unlock(String key, String value) {
        return true;
    }
}