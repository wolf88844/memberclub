/**
 * @(#)UserTagOpTypeEnum.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.usertag;

/**
 * @author yuhaiqiang
 */
public enum UserTagOpTypeEnum {

    ADD(1, "增加"),
    DEL(2, "删除"),
    GET(3, "查询"),
    ;

    private int code;

    private String name;

    UserTagOpTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserTagOpTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (UserTagOpTypeEnum item : UserTagOpTypeEnum.values()) {
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
