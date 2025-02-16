/**
 * @(#)RenewTypeEnum.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase.common;

/**
 * @author wuyang
 */
public enum RenewTypeEnum {

    NONE(0, "无续费"),
    USER_RENEW(1, "用户主动续费"),
    SYSTEM_RENEW(2, "系统自动续费"),
    ;

    private int code;

    private String name;

    RenewTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RenewTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (RenewTypeEnum item : RenewTypeEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }
}
