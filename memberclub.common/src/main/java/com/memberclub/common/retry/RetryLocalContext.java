/**
 * @(#)RetryLocal.java, 十二月 31, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

/**
 * author: 掘金五阳
 */
public class RetryLocalContext {

    public static final ThreadLocal<RetryLocalContext> LOCAL_CONTEXT = new ThreadLocal<>();

    public int retryTimes = 0;


    public static ThreadLocal<RetryLocalContext> getLocalContext() {
        if (LOCAL_CONTEXT.get() == null) {
            LOCAL_CONTEXT.set(new RetryLocalContext());
        }
        return LOCAL_CONTEXT;
    }

    public static void clear() {
        LOCAL_CONTEXT.remove();
    }

    public static int getRetryTimes() {
        return getLocalContext().get().retryTimes;
    }

    public static void setRetryTimes(int retryTimes) {
        getLocalContext().get().retryTimes = retryTimes;
    }
}