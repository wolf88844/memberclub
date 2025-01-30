/**
 * @(#)RestrictItemType.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku.restrict;

/**
 * @author yuhaiqiang
 */
public enum RestrictItemType {

    TOTAL(0, "total"),
    SKU(1, "sku"),
    ;

    private int code;

    private String name;

    RestrictItemType(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RestrictItemType findByCode(int code) throws IllegalArgumentException {
        for (RestrictItemType item : RestrictItemType.values()) {
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
