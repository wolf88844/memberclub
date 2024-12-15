/**
 * @(#)BuyTypeEnum.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author yuhaiqiang
 */
public enum SaleTypeEnum {

    DIRECT_SALE(1, "direct_sale"),//直售
    TIED_SALE(2, "tied_sale"),//搭售
    OUT_SALE(3, "out_sale"),//外部售卖
    ;

    private int value;

    private String name;

    SaleTypeEnum(int value, String name) {
        this.value = value;
        this.name = name;
    }

    public static SaleTypeEnum findByInt(int value) throws IllegalArgumentException {
        for (SaleTypeEnum item : SaleTypeEnum.values()) {
            if (item.value == value) {
                return item;
            }
        }

        throw new IllegalArgumentException("Invalid BuyTypeEnum value: " + value);
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int toInt() {
        return this.value;
    }
}
