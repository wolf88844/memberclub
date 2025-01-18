/**
 * @(#)RetryLocal.java, 十二月 31, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

import com.google.common.collect.Maps;
import lombok.Getter;

import java.util.Map;

/**
 * author: 掘金五阳
 */
public class RetryLocalContext {

    public static final ThreadLocal<RetryLocalContext> LOCAL_CONTEXT = new ThreadLocal<>();

    /**
     * 使用 Map 是为了避免多个Retryable 注解重叠时,分别记录每个方法的重试次数.
     */
    @Getter
    public Map<String, Integer> method2RetryTimes = Maps.newHashMapWithExpectedSize(2);


    public static ThreadLocal<RetryLocalContext> getLocalContext() {
        if (LOCAL_CONTEXT.get() == null) {
            LOCAL_CONTEXT.set(new RetryLocalContext());
        }
        return LOCAL_CONTEXT;
    }

    public static void clear() {
        LOCAL_CONTEXT.remove();
    }

    public static int getRetryTimes(String beanName, String method) {
        return getLocalContext().get().getMethod2RetryTimes()
                .getOrDefault(String.format("%s.%s", beanName, method), 0);
    }

    public static void setRetryTimes(String beanName, String method, int retryTimes) {
        getLocalContext().get().getMethod2RetryTimes()
                .put(String.format("%s.%s", beanName, method), retryTimes);
    }
}