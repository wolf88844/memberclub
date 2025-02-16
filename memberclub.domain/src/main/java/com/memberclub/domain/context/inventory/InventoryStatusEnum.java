/**
 * @(#)InventoryStatusEnum.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.inventory;

/**
 * @author wuyang
 */
public enum InventoryStatusEnum {


    ACTIVTE(0, "上架中"),
    INACTIVE(999, "已下架"),
    ;

    private int code;

    private String name;

    InventoryStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InventoryStatusEnum findByCode(int code) throws IllegalArgumentException {
        for (InventoryStatusEnum item : InventoryStatusEnum.values()) {
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
