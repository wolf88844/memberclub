/**
 * @(#)DemoMemberBizSceneBuildExtension.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.config;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.BizSceneBuildExtension;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "demo 会员 bizScene构建", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DemoMemberBizSceneBuildExtension implements BizSceneBuildExtension {


    @Override
    public String buildBuildPerformContextScene(PerformContext context) {
        if (context.getSkuBuyDetails().get(0).getPerformConfig().getConfigs().get(0).getCycle() > 1) {
            return SceneEnum.SCENE_MUTIL_PERIOD_CARD.getValue();
        }
        return SceneEnum.SCENE_MONTH_CARD.getValue();
    }

    @Override
    public String buildPerformItemGrantExtensionScene(PerformItemContext context) {
        return context.getRightType() + "";
    }
}