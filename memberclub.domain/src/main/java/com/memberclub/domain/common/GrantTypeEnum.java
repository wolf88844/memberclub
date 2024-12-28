/**
 * @(#)GrantTypeEnum.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum GrantTypeEnum {

    GRANT(0, "发放"),
    ACTIVATE(1, "激活");

    private int value;

    private String name;

    GrantTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static GrantTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (GrantTypeEnum item : GrantTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid GrantTypeEnum value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
