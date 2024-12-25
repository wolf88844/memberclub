/**
 * @(#)BizSecneBuildExtension.java, 十二月 16, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.extension;

import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;

/**
 * author: 掘金五阳
 */
public interface BizSceneBuildExtension extends BaseExtension {

    default String buildPerformItemGrantExtensionScene(PerformItemContext context) {
        return String.valueOf(context.getItems().get(0).getRightType().toInt());
    }

    default String buildPreBuildPerformContextScene(PerformCmd cmd) {
        return SceneEnum.DEFAULT_SCENE.getValue();
    }

    default String buildBuildPerformContextScene(PerformContext context) {
        return SceneEnum.SCENE_MONTH_CARD.getValue();
    }

    default String buildPerformContextExecuteScene(PerformContext performContext) {
        return SceneEnum.SCENE_MONTH_CARD.getValue();
    }

    default String buildAftersalePreviewScene(AftersalePreviewContext context) {
        return SceneEnum.SCENE_AFTERSALE_MONTH_CARD.getValue();
    }

    default String buildAftersaleApplyScene(AfterSaleApplyContext context) {
        return SceneEnum.SCENE_AFTERSALE_MONTH_CARD.getValue();
    }
}