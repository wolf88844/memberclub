/**
 * @(#)BizScene.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author 掘金五阳
 */
@Data
@AllArgsConstructor
public class BizScene {

    public int bizType;

    public String scene;

    public static BizScene of(int bizType, String scene) {
        return new BizScene(bizType, scene);
    }

    public String getKey() {
        return String.format("%s_%s", bizType, scene);
    }
}