/**
 * @(#)DefaultPreFinanceMessageBuildExtension.java, 一月 26, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.extension.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.sdk.prefinance.extension.PreFinanceMessageBuildExtension;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认的预结算消息构建扩展点", bizScenes = {@Route(bizType = BizTypeEnum.DEFAULT)})
public class DefaultPreFinanceMessageBuildExtension implements PreFinanceMessageBuildExtension {

    
}