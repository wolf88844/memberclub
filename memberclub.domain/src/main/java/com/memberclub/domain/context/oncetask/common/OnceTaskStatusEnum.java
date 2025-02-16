/**
 * @(#)OnceTaskStatusEnum.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.oncetask.common;

/**
 * @author wuyang
 */
public enum OnceTaskStatusEnum {

    INIT(0, "初始化"),
    PROCESSING(9, "执行中"),
    CANCEL(19, "取消"),
    FAIL(29, "失败"),
    SUCCESS(39, "成功");

    private int value;

    private String name;

    OnceTaskStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static OnceTaskStatusEnum findByCode(int value) throws IllegalArgumentException {
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

    public int getCode() {
        return this.value;
    }
}
