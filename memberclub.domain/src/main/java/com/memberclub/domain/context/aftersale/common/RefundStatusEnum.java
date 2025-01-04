/**
 * @(#)RefundStatusEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.common;

/**
 * @author yuhaiqiang
 */
public enum RefundStatusEnum {

    INIT(0, "未退"),
    PORTION_REFUNDED(1, "已部分退"),
    ALL_REFUNDED(2, "已全部退"),
    ;

    private int value;

    private String name;

    RefundStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static RefundStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (RefundStatusEnum item : RefundStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid RefundStatusEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
