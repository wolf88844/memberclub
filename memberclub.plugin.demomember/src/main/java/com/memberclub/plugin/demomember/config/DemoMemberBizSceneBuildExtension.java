/**
 * @(#)DemoMemberBizSceneBuildExtension.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.config;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.BizSceneBuildExtension;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "demo 会员 bizScene构建", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DemoMemberBizSceneBuildExtension implements BizSceneBuildExtension {
}