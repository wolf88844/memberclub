/**
 * @(#)DemoMemberExpirePreFinanceTriggerExtension.java, 一月 28, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.prefinance;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.sdk.oncetask.prefinance.extension.DefaultPreFinanceExpireTriggerExtension;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 预结算过期事件触发扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.FINANCE_EXPIRE_TASK_TYPE)
})
public class DemoMemberExpirePreFinanceTriggerExtension extends DefaultPreFinanceExpireTriggerExtension {
}