/**
 * @(#)SceneEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.biz;

/**
 * @author yuhaiqiang
 */
public enum SceneEnum {

    DEFAULT_SCENE("1");

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
