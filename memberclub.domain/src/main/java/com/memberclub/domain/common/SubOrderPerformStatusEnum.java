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
public enum SubOrderPerformStatusEnum {

    INIT(0, "init"),
    IMMEDIATE_PERFORMING(10, "immediate_performing"),
    IMMEDIATE_PERFORED(15, "immediate_performed"),
    DELAY_PERFORMING(20, "delay_performing"),
    DELAY_PERFORMED(25, "delay_performed"),
    PERFORM_FAIL(36, "perform_fail"),
    PERFORM_SUCC(35, "perform_succ"),
    REVEREING(40, "reversing"),
    PORTION_REVERSED(44, "portion_reversed"),
    COMPLETED_REVERSED(45, "completed_reversed"),
    ;

    private int value;

    private String name;

    SubOrderPerformStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SubOrderPerformStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (SubOrderPerformStatusEnum item : SubOrderPerformStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid SubOrderStatusEnum value: " + value);
    }

    public static boolean hasPerformed(int status) {
        return status >= PERFORM_SUCC.toInt();
    }


    public static int getReversedStatus(boolean completed) {
        return completed ? COMPLETED_REVERSED.toInt() : PORTION_REVERSED.toInt();
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
