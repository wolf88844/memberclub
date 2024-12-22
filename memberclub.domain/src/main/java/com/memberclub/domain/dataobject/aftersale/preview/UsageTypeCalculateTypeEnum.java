/**
 * @(#)UsageTypeCalculateTypeEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale.preview;

/**
 * @author yuhaiqiang
 */
public enum UsageTypeCalculateTypeEnum {

    USE_AMOUNT(1, "金额方式"),
    USE_STATUS(2, "使用状态"),

    ;

    private int value;

    private String name;

    UsageTypeCalculateTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static UsageTypeCalculateTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (UsageTypeCalculateTypeEnum item : UsageTypeCalculateTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid UsageTypeCalculateTypeEnum value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
