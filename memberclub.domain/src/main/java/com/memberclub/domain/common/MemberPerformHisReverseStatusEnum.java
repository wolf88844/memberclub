/**
 * @(#)MemberPerformHisReverseStatusEnum.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum MemberPerformHisReverseStatusEnum {


    INIT(0, "init"),
    TASK_REVERSING(40, "task_reversing"),
    TASK_REVERSED(45, "task_reversed"),
    ASSET_REVERSING(50, "asset_reversing"),
    ASSET_PORTION_REVERSED(54, "asset_portion_reversed"),
    ASSET_COMPLETED_REVERSED(55, "asset_completed_reversed"),
    REVERSED(60, "reversed"),
    ;

    private int value;

    private String name;

    MemberPerformHisReverseStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static MemberPerformHisReverseStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (MemberPerformHisReverseStatusEnum item : MemberPerformHisReverseStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid MemberPerformHisReverseStatusEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
