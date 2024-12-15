/**
 * @(#)DefaultPerformExtension.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.biz.BizTypeEnum;
import com.memberclub.common.biz.SceneEnum;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.log.CommonLog;
import com.memberclub.sdk.extension.PerformExtension;

/**
 * @author 掘金五阳
 */
@ExtensionImpl(desc = "默认履约扩展点", bizScenes = {@Route(bizType = BizTypeEnum.DEMO_MEMBER, scene = SceneEnum.DEFAULT_SCENE)})
public class DefaultPerformExtension implements PerformExtension {

    @Override
    public void execute() throws Exception {
        CommonLog.info("接收到 execute方法");
    }
}