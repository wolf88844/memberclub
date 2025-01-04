/**
 * @(#)IdTypeEnum.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.id;

/**
 * @author yuhaiqiang
 */
public enum IdTypeEnum {

    PERFORM_HIS(1, "perform_his"),
    AFTERSALE_ORDER(2, "aftersale_order"),
    PURCHASE_TRADE(3, "purchase_trade"),
    ;

    private int value;

    private String name;

    IdTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static IdTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (IdTypeEnum item : IdTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid IdTypeEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
