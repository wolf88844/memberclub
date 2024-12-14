/**
 * @(#)BizScene.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.annotation;

import com.memberclub.common.biz.BizTypeEnum;
import com.memberclub.common.biz.SceneEnum;

/**
 * @author 掘金五阳
 */
public @interface Route {

    public BizTypeEnum bizType();

    public SceneEnum scene() default SceneEnum.DEFAULT_SCENE;
}