/**
 * @(#)BizTypeEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author 掘金五阳
 */
public enum BizTypeEnum {

    DEMO_MEMBER(1, "demo_member");

    private int value;

    private String name;

    BizTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public boolean isEqual(int bizType) {
        return this.value == bizType;
    }


    public static BizTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (BizTypeEnum item : BizTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid BizTypeEnum value: " + value);
    }

    public static BizTypeEnum findByString(String name) throws IllegalArgumentException {
        for (BizTypeEnum item : BizTypeEnum.values()) {
            if (item.name.equals(name)) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid BizTypeEnum name: " + name);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toBizType() {
        return this.value;
    }
}
