/**
 * @(#)RetryMessage.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

import com.memberclub.common.util.TimeUtil;
import lombok.Data;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */
@Data
public class RetryMessage implements Delayed {

    private String beanName;

    private String beanClassName;

    private String methodName;

    private String args;

    private String argsClassName;

    private int retryTimes;

    private long expectedTime;

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.toMillis(expectedTime - TimeUtil.now());
    }

    @Override
    public int compareTo(Delayed o) {
        return expectedTime < o.getDelay(TimeUnit.MILLISECONDS) ? -1 : 1;
    }
}