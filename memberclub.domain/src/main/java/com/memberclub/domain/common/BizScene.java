/**
 * @(#)BizScene.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.common;

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

    public static BizScene of(int bizType) {
        return new BizScene(bizType, SceneEnum.DEFAULT_SCENE.getValue());
    }

    public static BizScene of(BizTypeEnum bizType, String scene) {
        return new BizScene(bizType.getCode(), scene);
    }

    public static BizScene of(BizTypeEnum bizType) {
        return of(bizType.getCode(), SceneEnum.DEFAULT_SCENE.getValue());
    }

    public String getKey() {
        return String.format("%s_%s", bizType, scene);
    }
}