/**
 * @(#)UsageTypeEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.contant;

/**
 * @author wuyang
 */
public enum UsageTypeEnum {

    UNUSE(1, "未使用"),
    USED(2, "已使用"),
    USEOUT(3, "已用尽"),
    ;

    private int value;

    private String name;

    UsageTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static UsageTypeEnum findByCode(int value) throws IllegalArgumentException {
        for (UsageTypeEnum item : UsageTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid UsageTypeEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.value;
    }
}
