/**
 * @(#)PreFinanceEventEnum.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.prefinance;

/**
 * @author yuhaiqiang
 */
public enum FinanceEventEnum {

    PERFORM(1, "履约"),
    FREEZE_NON_REFUND(2, "仅冻结不退款"),
    REFUND(3, "退款"),
    ;

    private int code;

    private String name;

    FinanceEventEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static FinanceEventEnum findByCode(int code) throws IllegalArgumentException {
        for (FinanceEventEnum item : FinanceEventEnum.values()) {
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
