/**
 * @(#)CacheEnum.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.cache;

import lombok.Getter;

/**
 * @author yuhaiqiang
 */
public enum CacheEnum {

    inventory(1, "member_inventory_cache"),
    membership(2, "member_ship_cache"),
    //
    ;

    private int code;

    @Getter
    private String name;

    CacheEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CacheEnum findByCode(int code) throws IllegalArgumentException {
        for (CacheEnum item : CacheEnum.values()) {
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
