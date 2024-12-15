/**
 * @(#)PerformHisStatusEnum.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum MemberPerformHisStatusEnum {

    INIT(0, "init"),
    IMMEDIATE_PERFORMING(10, "immediate_performing"),
    IMMEDIATE_PERFORED(19, "immediate_performed"),
    DELAY_PERFORMING(20, "delay_performing"),
    DELAY_PERFORMED(29, "delay_performed"),
    PERFORM_FAIL(36, "perform_fail"),
    PERFORM_SUCC(39, "perform_succ");

    private int value;

    private String name;

    MemberPerformHisStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static MemberPerformHisStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (MemberPerformHisStatusEnum item : MemberPerformHisStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid PerformHisStatusEnum value: " + value);
    }

    public static boolean hasPerformed(int status) {
        return status >= PERFORM_SUCC.toInt();
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
