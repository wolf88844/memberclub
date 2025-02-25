/**
 * @(#)BizTypeEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

import lombok.Getter;

/**
 * BizTypeEnum 是一个枚举类，用于定义业务类型。
 * 每个枚举值包含一个代码（code）和名称（name），用于标识不同的业务场景。
 * 提供了根据代码查找枚举值的方法以及一些辅助方法。
 *
 * @author 掘金五阳
 */
public enum BizTypeEnum {

    /**
     * 默认业务类型，默认值为0，名称为"default_biz"。
     */
    DEFAULT(0, "default_biz"),

    /**
     * 示例会员业务类型，代码为1，名称为"demo_member"。
     */
    DEMO_MEMBER(1, "demo_member"),

    /**
     * 示例优惠套餐业务类型，代码为2，名称为"demo_coupon_package"。
     */
    DEMO_COUPON_PACKAGE(2, "demo_coupon_package");

    /**
     * 枚举项的代码，用于唯一标识每个业务类型。
     */
    private int code;

    /**
     * 枚举项的名称，用于描述业务类型的含义。
     */
    @Getter
    private String name;

    /**
     * 构造函数，初始化枚举项的代码和名称。
     *
     * @param code 业务类型的代码
     * @param name 业务类型的名称
     */
    BizTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    /**
     * 检查当前枚举项的代码是否等于给定的业务类型代码。
     *
     * @param bizType 要比较的业务类型代码
     * @return 如果相等返回true，否则返回false
     */
    public boolean isEqual(int bizType) {
        return this.code == bizType;
    }

    /**
     * 根据代码查找对应的枚举项。如果找不到匹配的枚举项，则抛出IllegalArgumentException异常。
     *
     * @param value 要查找的业务类型代码
     * @return 匹配的枚举项
     * @throws IllegalArgumentException 如果找不到匹配的枚举项
     */
    public static BizTypeEnum findByCode(int value) throws IllegalArgumentException {
        for (BizTypeEnum item : BizTypeEnum.values()) {
            if (item.code == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid BizTypeEnum code: " + value);
    }

    /**
     * 重写toString方法，返回枚举项的名称。
     *
     * @return 枚举项的名称
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * 获取枚举项的代码。
     *
     * @return 枚举项的代码
     */
    public int getCode() {
        return this.code;
    }
}
