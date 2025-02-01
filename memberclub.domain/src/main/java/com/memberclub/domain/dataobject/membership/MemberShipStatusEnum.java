/**
 * @(#)MemberShipStatusEnum.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.membership;

/**
 * @author yuhaiqiang
 */
public enum MemberShipStatusEnum {

    INIT(0, "初始化"),
    FINISH(1, "完成"),
    CANCEL(2, "取消"),
    //
    ;

    private int code;

    private String name;

    MemberShipStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MemberShipStatusEnum findByCode(int code) throws IllegalArgumentException {
        for (MemberShipStatusEnum item : MemberShipStatusEnum.values()) {
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
