/**
 * @(#)MemberOrderStatusEnum.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum MemberOrderStatusEnum {

    INIT(0, "init"),
    SUBMITED(9, "submited"),
    CANCELED(19, "canceled"),
    PAYED(29, "payed"),
    PERFORMING(30, "performing"),
    PERFORM(39, "performed"),
    REFUNDED(49, "refunded");

    private int value;

    private String name;

    MemberOrderStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Boolean isPerformEnabled(int status) {
        return status < PERFORM.toInt();
    }

    public static MemberOrderStatusEnum findByInt(int value) throws IllegalArgumentException {
        for (MemberOrderStatusEnum item : MemberOrderStatusEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid MemberOrderStatusEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
