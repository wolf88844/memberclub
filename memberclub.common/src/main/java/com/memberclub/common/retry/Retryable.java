/**
 * @(#)retryable.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

/**
 * @author yuhaiqiang
 */
public @interface Retryable {

    public int initialDelaySeconds() default 5;

    public int maxDelaySeconds() default 60;

    public int maxTimes() default 10;

    public boolean hasFallback() default false;
}
