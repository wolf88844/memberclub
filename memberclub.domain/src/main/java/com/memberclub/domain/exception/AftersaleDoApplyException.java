/**
 * @(#)AftersaleDoApplyException.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.exception;

/**
 * author: 掘金五阳
 */
public class AftersaleDoApplyException extends MemberException {

    public AftersaleDoApplyException(ResultCode code) {
        super(code);
    }

    public AftersaleDoApplyException(ResultCode code, Throwable cause) {
        super(code, cause);
    }

    public AftersaleDoApplyException(ResultCode code, String msg) {
        super(code, msg);
    }

    public AftersaleDoApplyException(ResultCode code, String msg, Exception e) {
        super(code, msg, e);
    }
}