/**
 * @(#)InventoryOpTypeEnum.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.inventory;

/**
 * @author wuyang
 */
public enum InventoryOpTypeEnum {

    DECREMENT(1, "扣减库存"),

    ROLLBACK(2, "回补库存");

    private int code;

    private String name;

    InventoryOpTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InventoryOpTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (InventoryOpTypeEnum item : InventoryOpTypeEnum.values()) {
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
