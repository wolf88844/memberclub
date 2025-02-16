/**
 * @(#)MemberPerformHisReverseStatusEnum.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.common;

/**
 * @author wuyang
 */
public enum MemberSubOrderReverseStatusEnum {


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

    MemberSubOrderReverseStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static MemberSubOrderReverseStatusEnum findByCode(int value) throws IllegalArgumentException {
        for (MemberSubOrderReverseStatusEnum item : MemberSubOrderReverseStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid SubOrderReverseStatusEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.value;
    }
}
