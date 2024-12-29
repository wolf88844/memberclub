/**
 * @(#)TaskTypeEnum.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum TaskTypeEnum {

    PERIOD_PERFORM(1, "周期履约"),
    SETTLE_EXPIRE(1, "结算过期任务"),
    AFTERSALE_EXPIRE_REFUND(1, "售后过期退"),
    ;

    private int value;

    private String name;

    TaskTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static TaskTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (TaskTypeEnum item : TaskTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid TaskTypeEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
