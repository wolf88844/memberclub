/**
 * @(#)UserTagKeyEnum.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.usertag;

import lombok.Getter;

/**
 * @author yuhaiqiang
 */
public enum UserTagKeyEnum {

    USERID(1, "u"),
    BIZTYPE(2, "b"),
    USER_TAG_TYPE(3, "t"),
    PERIOD_TYPE(4, "pt"),
    ITEM_TYPE(5, "it"),
    ;

    private int code;

    @Getter
    private String name;

    UserTagKeyEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static UserTagKeyEnum findByCode(int code) throws IllegalArgumentException {
        for (UserTagKeyEnum item : UserTagKeyEnum.values()) {
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
