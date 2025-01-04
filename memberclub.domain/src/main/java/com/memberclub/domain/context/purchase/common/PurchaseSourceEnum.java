/**
 * @(#)PurchaseSourceEnum.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase.common;

/**
 * @author yuhaiqiang
 */
public enum PurchaseSourceEnum {

    HOMEPAGE(1, "主页"),
    
    ;

    private int code;

    private String name;

    PurchaseSourceEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static PurchaseSourceEnum findByInt(int code) throws IllegalArgumentException {
        for (PurchaseSourceEnum item : PurchaseSourceEnum.values()) {
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
