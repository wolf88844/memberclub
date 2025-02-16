/**
 * @(#)InventoryTargetTypeEnum.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity.inventory;

/**
 * @author wuyang
 */
public enum InventoryTargetTypeEnum {

    SKU(1, "商品库存"),
    ;

    private int code;

    private String name;

    InventoryTargetTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static InventoryTargetTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (InventoryTargetTypeEnum item : InventoryTargetTypeEnum.values()) {
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
