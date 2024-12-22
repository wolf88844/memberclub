/**
 * @(#)AftersaleUnableCode.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale;

import com.memberclub.domain.exception.AfterSaleUnableException;
import org.springframework.util.StringUtils;

/**
 * @author yuhaiqiang
 */
public enum AftersaleUnableCode {

    INTERNAL_ERROR(1, "内部错误"),

    DEGRADE_AFTERSALE_ERROR(1001, "降级异常,不能退"),
    AFTERSALE_TIMES_EXCEED_ERROR(1002, "超过当天发起售后最大次数"),
    EXPIRE_ERROR(1003, "已过期,不能退"),
    USE_OUT_ERROR(1004, "已用尽,不能退"),
    USE_ERROR(1005, "已使用,不能退"),
    REFUNDED(1006, "已退款"),
    NON_PERFORMED(1007, "还未履约成功,不能退"),
    ;

    private int value;

    private String name;

    AftersaleUnableCode(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AftersaleUnableCode findByInt(int value) throws IllegalArgumentException {
        for (AftersaleUnableCode item : AftersaleUnableCode.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid AftersaleUnableCode value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }

    public void throwException(String msg, Throwable cause) {
        msg = StringUtils.isEmpty(msg) ? this.toString() : msg;
        AfterSaleUnableException e = new AfterSaleUnableException(this, msg, cause);
        throw e;
    }

    public void throwException() {
        AfterSaleUnableException e = new AfterSaleUnableException(this, this.toString(), null);
        throw e;
    }
}
