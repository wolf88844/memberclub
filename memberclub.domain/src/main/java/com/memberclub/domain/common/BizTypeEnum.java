/**
 * @(#)BizTypeEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author 掘金五阳
 */
public enum BizTypeEnum {

    DEFAULT(0, "default_biz"),
    DEMO_MEMBER(1, "demo_member"),
    DEMO_COUPON_PACKAGE(2, "demo_coupon_package"),
    ;

    private int code;

    private String name;

    BizTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public boolean isEqual(int bizType) {
        return this.code == bizType;
    }


    public static BizTypeEnum findByCode(int value) throws IllegalArgumentException {
        for (BizTypeEnum item : BizTypeEnum.values()) {
            if (item.code == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid BizTypeEnum code: " + value);
    }

    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }
}
