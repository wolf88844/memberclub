/**
 * @(#)PreFinanceEventEnum.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance.common;

import lombok.Getter;

/**
 * @author yuhaiqiang
 */
public enum PreFinanceEventEnum {

    PERFORM(1, "履约"),
    FREEZE_NON_REFUND(2, "仅冻结不退款"),
    REFUND(3, "退款"),
    EXPIRE(4, "过期"),
    ;

    private int code;

    @Getter
    private String name;

    PreFinanceEventEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PreFinanceEventEnum findByCode(int code) throws IllegalArgumentException {
        for (PreFinanceEventEnum item : PreFinanceEventEnum.values()) {
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
