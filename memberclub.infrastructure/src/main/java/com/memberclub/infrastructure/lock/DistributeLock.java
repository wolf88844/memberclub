/**
 * @(#)DistributeLock.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.lock;

/**
 * author: 掘金五阳
 */
public interface DistributeLock {

    boolean lock(String key, Long value, int timeSeconds);

    void unlock(String key, Long value);
}