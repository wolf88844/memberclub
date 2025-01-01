/**
 * @(#)SceneEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

/**
 * @author 掘金五阳
 */
public enum SceneEnum {

    DEFAULT_SCENE("1"),

    RIGHT_TYPE_SCENE_COUPON("1"),

    RIGHT_TYPE_SCENE_DISCOUNT_COUPON("2"),

    SCENE_MONTH_CARD("201"),//履约上下文执行 scene, 月卡部分

    SCENE_MUTIL_PERIOD_CARD("202"),//履约上下文执行 scene, 多周期卡部分

    SCENE_AFTERSALE_MONTH_CARD("301"),//售后月卡

    SCENE_AFTERSALE_MUTIL_PERIOD_CARD("302"),//售后多周期卡

    SCENE_AFTERSALE_RENEW("304"),//售后续费

    /*SCENE_AFTERSALE_SOURCE_USER("30" + AftersaleSourceEnum.User.toInt()),//用户渠道

    SCENE_AFTERSALE_SOURCE_CUSTOMER_SERVICE("30" + AftersaleSourceEnum.Customer_Service.toInt()),//客服

    SCENE_AFTERSALE_SOURCE_ADMIN("30" + AftersaleSourceEnum.Admin.toInt()),//Admin

    SCENE_AFTERSALE_SOURCE_SYSTEM_EXPIRE("30" + AftersaleSourceEnum.System_Expire.toInt()),//系统过期退*/;

    private String value;

    SceneEnum(String value) {
        this.value = value;
    }


    public static String buildRightTypeScene(int rightType) {
        return "" + rightType;
    }

    @Override
    public String toString() {
        return this.value;
    }

    public String getValue() {
        return this.value;
    }

}
