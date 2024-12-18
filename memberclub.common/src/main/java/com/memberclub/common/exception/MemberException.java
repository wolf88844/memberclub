/**
 * @(#)MemberException.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.exception;

/**
 * @author 掘金五阳
 */
public class MemberException extends RuntimeException {

    ResultCode code;

    public MemberException(ResultCode code) {
        super(code.getMsg());
        this.code = code;
    }


    /**
     * Constructs a new runtime exception with the specified detail message and
     * cause.  <p>Note that the detail message associated with
     * {@code cause} is <i>not</i> automatically incorporated in
     * this runtime exception's detail message.
     *
     * @param code  the   detail message (which is saved for later retrieval
     *              by the {@link #getMessage()} method).
     * @param cause the cause (which is saved for later retrieval by the
     *              {@link #getCause()} method).  (A <tt>null</tt> value is
     *              permitted, and indicates that the cause is nonexistent or
     *              unknown.)
     * @since 1.4
     */
    public MemberException(ResultCode code, Throwable cause) {
        super(code.toString(), cause);
        this.code = code;
    }

    public MemberException(ResultCode code, String msg) {
        super(msg);
        this.code = code;
    }

    public static void throwException(ResultCode code) {
        MemberException exception = new MemberException(code);
        throw exception;
    }

    public static void throwException(ResultCode code, String msg) {
        MemberException exception = new MemberException(code, msg);
        throw exception;
    }
}