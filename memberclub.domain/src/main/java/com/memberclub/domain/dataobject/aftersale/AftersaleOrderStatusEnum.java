/**
 * @(#)AftersaleOrderStatusEnum.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale;

/**
 * @author yuhaiqiang
 */
public enum AftersaleOrderStatusEnum {

    INIT(1, "初始化"),
    REVERSE_PERFORM_ING(10, "逆向履约中"),
    REVERSE_PERFORM_SUCC(15, "逆向履约成功"),
    REVERSE_BUY_ING(20, "逆向购买中"),
    REVERSE_BUY_SUCC(25, "逆向购买成功"),
    REVERSE_REFUND_ING(30, "逆向退款中"),
    REVERSE_REFUND_SUCC(35, "逆向退款成功"),
    AFTERSALE_SUCC(55, "售后成功"),

    ;


    private int value;

    private String name;

    AftersaleOrderStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AftersaleOrderStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (AftersaleOrderStatusEnum item : AftersaleOrderStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        return null;
    }

    public boolean isEquals(int status) {
        return value == status;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
