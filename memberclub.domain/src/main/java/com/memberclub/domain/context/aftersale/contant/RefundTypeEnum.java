/**
 * @(#)RefundType.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.contant;

/**
 * @author yuhaiqiang
 */
public enum RefundTypeEnum {

    ALL_REFUND(1, "全部退"),

    PORTION_RFUND(2, "部分退"),
    ;

    private int value;

    private String name;

    RefundTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static RefundTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (RefundTypeEnum item : RefundTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid RefundType value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
