/**
 * @(#)RestrictUserTypeEnum.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku.restrict;

/**
 * @author yuhaiqiang
 */
public enum RestrictUserTypeEnum {

    USERID(0, "userId");

    private int code;

    private String name;

    RestrictUserTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static RestrictUserTypeEnum findByCode(int code) throws IllegalArgumentException {
        for (RestrictUserTypeEnum item : RestrictUserTypeEnum.values()) {
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
