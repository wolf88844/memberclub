/**
 * @(#)PreFinanceStatusEnum.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance.common;

/**
 * @author yuhaiqiang
 */
public enum PreFinanceRecordStatusEnum {

    PAYED(5, "已支付"),

    PERFORM(15, "已履约"),

    JUST_FREEZE(25, "仅冻结"),

    REFUND(35, "退款"),

    EXPIRE(45, "已过期"),
    ;

    private int code;

    private String name;

    PreFinanceRecordStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PreFinanceRecordStatusEnum findByCode(int code) throws IllegalArgumentException {
        for (PreFinanceRecordStatusEnum item : PreFinanceRecordStatusEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }
}
