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

    INIT(0, "初始化"),
    SUBMITED(9, "已提单"),
    CANCELED(19, "已取消"),
    PAYED(29, "已支付"),
    PERFORMING(30, "履约中"),
    PERFORMED(35, "已履约"),
    REFUNDED(49, "已退款");

    private int value;

    private String name;

    MemberOrderStatusEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static Boolean isPerformEnabled(int status) {
        return status < PERFORMED.toInt();
    }

    public static Boolean isPerformed(int status) {
        return status == PERFORMED.toInt();
    }

    public static boolean nonPerformed(int status) {
        return status < PERFORMED.toInt();
    }

    public static boolean isRefunded(int status) {
        return status == MemberOrderStatusEnum.REFUNDED.toInt();
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
