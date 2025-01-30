/**
 * @(#)RestrictPeriodType.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku.restrict;

/**
 * @author yuhaiqiang
 */
public enum RestrictPeriodType {

    TOTAL(0, "total"),
    DAY(1, "day"),
    WEEK(2, "week"),
    MONTH(3, "month"),
    YEAR(4, "year"),
    TIME_RANGE_DAY(5, "time_range_day"),
    ;

    private int code;

    private String name;

    RestrictPeriodType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RestrictPeriodType findByCode(int code) throws IllegalArgumentException {
        for (RestrictPeriodType item : RestrictPeriodType.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name();
    }

    public int getCode() {
        return this.code;
    }
}
