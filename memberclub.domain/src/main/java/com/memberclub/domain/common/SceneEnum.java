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

    RIGHT_TYPE_SCENE_COUPON("101"),

    SCENE_MONTH_CARD("201"),//履约上下文执行 scene, 月卡部分

    SCENE_MUTIL_PERIOD_CARD("202"),//履约上下文执行 scene, 多周期卡部分

    SCENE_MUTIL_BUY_COUNT_CARD("203"),//履约上下文执行 scene, 多份数购买
    ;

    private String name;

    SceneEnum(String name) {
        this.name = name;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public String getName() {
        return this.name;
    }

}
