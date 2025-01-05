/**
 * @(#)AfterSaleUnableException.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.exception;

import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import lombok.Data;
import org.springframework.util.StringUtils;

/**
 * author: 掘金五阳
 */
@Data
public class AfterSaleUnableException extends MemberException {

    private int unableCode;

    private AfterSaleUnableException(ResultCode code) {
        super(code);
    }

    private AfterSaleUnableException(ResultCode code, Throwable cause) {
        super(code, cause);
    }

    private AfterSaleUnableException(ResultCode code, String msg) {
        super(code, msg);
    }

    public AfterSaleUnableException(AftersaleUnableCode code, String msg, Throwable cause) {
        super(ResultCode.AFTERSALE_UNABLE_ERROR,
                StringUtils.isEmpty(msg) ? ResultCode.AFTERSALE_UNABLE_ERROR.getMsg() : msg);
        if (cause != null) {
            super.initCause(cause);
        }
        this.unableCode = code.getCode();
    }
}