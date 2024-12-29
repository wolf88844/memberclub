/**
 * @(#)OnceTaskStatusEnum.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common.status;

/**
 * @author yuhaiqiang
 */
public enum OnceTaskStatusEnum {

    INIT(0, "初始化"),
    PROCESSING(9, "执行中"),
    CANCEL(19, "取消"),
    FAIL(29, "失败"),
    SUCC(39, "成功");

    private int value;

    private String name;

    OnceTaskStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OnceTaskStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (OnceTaskStatusEnum item : OnceTaskStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid OnceTaskStatusEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
