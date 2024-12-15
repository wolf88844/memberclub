/**
 * @(#)BizScene.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.annotation;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author 掘金五阳
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Route {

    public BizTypeEnum bizType();

    public SceneEnum scene() default SceneEnum.DEFAULT_SCENE;
}