/**
 * @(#)InventoryTypeEnum.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

/**
 * @author yuhaiqiang
 */
public enum InventoryTypeEnum {

    TOTAL(0, "总量"),
    DAY(1, "每日"),
    WEEK(2, "每周"),
    MONTH(3, "每月"),
    YEAR(4, "每年"),
    TIME_RANGE_DAY(5, "每日某个时间段"),
    ;

    private int code;

    private String name;

    InventoryTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InventoryTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (InventoryTypeEnum item : InventoryTypeEnum.values()) {
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
