/**
 * @(#)BizSecneBuildExtension.java, 十二月 16, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.extension;

import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemContext;

/**
 * author: 掘金五阳
 */
public interface BizSceneBuildExtension extends BaseExtension {

    default String buildPerformItemGrantExtensionScene(PerformItemContext context) {
        return String.valueOf(context.getItems().get(0).getRightType());
    }

    default String buildPreBuildPerformContextScene(PerformCmd cmd) {
        return SceneEnum.DEFAULT_SCENE.getName();
    }

    default String buildBuildPerformContextScene(PerformCmd cmd) {
        return SceneEnum.DEFAULT_SCENE.getName();
    }

    default String buildPerformContextExecuteScene(PerformContext performContext) {
        return SceneEnum.SCENE_MONTH_CARD.getName();
    }
}