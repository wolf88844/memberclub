/**
 * @(#)UsageTypeEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale.preview;

/**
 * @author yuhaiqiang
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

    public static UsageTypeEnum findByInt(int value) throws IllegalArgumentException {
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

    public int toInt() {
        return this.value;
    }
}
