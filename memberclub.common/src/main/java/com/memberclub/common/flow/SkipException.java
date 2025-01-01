/**
 * @(#)SkipException.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.flow;

/**
 * author: 掘金五阳
 */
public class SkipException extends RuntimeException {
    public SkipException() {
        super();
    }

    public SkipException(String message) {
        super(message);
    }

    public SkipException(String message, Throwable cause) {
        super(message, cause);
    }

    public SkipException(Throwable cause) {
        super(cause);
    }

    protected SkipException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}