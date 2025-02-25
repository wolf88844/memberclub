/**
 * @(#)BizSceneBuildExtension.java, 十二月 16, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.extension;

import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;

/**
 * BizSceneBuildExtension 是一个接口，定义了用于构建业务场景（BizScene）的通用扩展点。
 * 每个方法根据不同的上下文返回相应的业务场景字符串，用于在不同业务逻辑中确定具体的执行场景。
 *
 * @author 掘金五阳
 */
@ExtensionConfig(desc = "构建BizScene 通用扩展点", type = ExtensionType.COMMON, must = false)
public interface BizSceneBuildExtension extends BaseExtension {

    /**
     * 构建执行项授权扩展场景。
     * 根据 {@link PerformItemContext} 中的第一个执行项的权利类型代码构建业务场景。
     *
     * @param context 执行项上下文
     * @return 业务场景字符串
     */
    default String buildPerformItemGrantExtensionScene(PerformItemContext context) {
        return String.valueOf(context.getItems().get(0).getRightType().getCode());
    }

    /**
     * 构建预构建执行上下文场景。
     * 默认返回默认场景。
     *
     * @param cmd 执行命令
     * @return 业务场景字符串
     */
    default String buildPreBuildPerformContextScene(PerformCmd cmd) {
        return SceneEnum.DEFAULT_SCENE.getValue();
    }

    /**
     * 构建拆分订单场景。
     * 默认返回月卡场景。
     *
     * @param context 执行上下文
     * @return 业务场景字符串
     */
    default String buildSeparateOrderScene(PerformContext context) {
        return SceneEnum.SCENE_MONTH_CARD.getValue();
    }

    /**
     * 构建执行上下文执行场景。
     * 默认返回月卡场景。
     *
     * @param performContext 执行上下文
     * @return 业务场景字符串
     */
    default String buildPerformContextExecuteScene(PerformContext performContext) {
        return SceneEnum.SCENE_MONTH_CARD.getValue();
    }

    /**
     * 构建售后预览场景。
     * 默认返回售后月卡场景。
     *
     * @param context 售后预览上下文
     * @return 业务场景字符串
     */
    default String buildAftersalePreviewScene(AftersalePreviewContext context) {
        return SceneEnum.SCENE_AFTERSALE_MONTH_CARD.getValue();
    }

    /**
     * 构建售后申请场景。
     * 默认返回售后月卡场景。
     *
     * @param context 售后申请上下文
     * @return 业务场景字符串
     */
    default String buildAftersaleApplyScene(AfterSaleApplyContext context) {
        return SceneEnum.SCENE_AFTERSALE_MONTH_CARD.getValue();
    }
}
