/**
 * @(#)AftersaleSourceEnum.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.contant;

/**
 * @author yuhaiqiang
 */
public enum AftersaleSourceEnum {

    User(1, "用户退"),
    Customer_Service(2, "人工客服退"),
    Admin(3, "管理员退"),
    System_Expire(4, "系统过期退");

    private int value;

    private String name;

    AftersaleSourceEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static AftersaleSourceEnum findByInt(int value) throws IllegalArgumentException {
        for (AftersaleSourceEnum item : AftersaleSourceEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid AftersaleSourceEnum value: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }

}
