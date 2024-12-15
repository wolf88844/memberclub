/**
 * @(#)OrderSystemTypeEnum.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;



/**
 * @author yuhaiqiang
 */
public enum OrderSystemTypeEnum {

    COMMON_ORDER(1, "common_order"),//订单中台,可根据实际情况进行扩展
    ;

    private int value;

    private String name;

    OrderSystemTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OrderSystemTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (OrderSystemTypeEnum item : OrderSystemTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid OrderSystemTypeEnum value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
