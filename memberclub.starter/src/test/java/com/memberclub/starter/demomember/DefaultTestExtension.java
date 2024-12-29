/**
 * @(#)DefaultPerformExtension.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.demomember;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformCmd;

/**
 * @author 掘金五阳
 */
@ExtensionImpl(desc = "默认履约扩展点", bizScenes = {@Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.DEFAULT_SCENE)})
public class DefaultTestExtension implements JustTestExtension {

    @UserLog(bizType = "bizType", tradeId = "tradeId", userId = "userId", domain = LogDomainEnum.PERFORM)
    @Override
    public void execute(PerformCmd cmd) throws Exception {
        CommonLog.info("接收到 execute方法");
    }
}