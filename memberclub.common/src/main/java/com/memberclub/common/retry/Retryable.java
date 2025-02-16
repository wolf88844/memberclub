/**
 * @(#)retryable.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuyang
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Retryable {

    public int initialDelaySeconds() default 5;

    public int maxDelaySeconds() default 10;

    public int maxTimes() default 5;

    public double multiplier() default 1.0;

    public boolean throwException() default false;

    public Class<Exception>[] include() default {Exception.class};

    public Class<Exception>[] exclude() default {};

    public boolean hasFallback() default false;
}
