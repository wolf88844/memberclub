/**
 * @(#)RefundWayEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.contant;

/**
 * @author wuyang
 */
public enum RefundWayEnum {

    ORDER_BACKSTRACK(1, "订单原路退"),

    CUSTOMER_REFUND(2, "人工客服赔付"),
    ;

    private int value;

    private String name;

    RefundWayEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static RefundWayEnum findByCode(int value) throws IllegalArgumentException {
        for (RefundWayEnum item : RefundWayEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid RefundWayEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.value;
    }
}
