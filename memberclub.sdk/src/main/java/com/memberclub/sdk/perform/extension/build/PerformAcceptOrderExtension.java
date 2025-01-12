/**
 * @(#)PerformContextBuildExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.build;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;

/**
 * author: 掘金五阳
 */

@ExtensionConfig(desc = "履约接单 扩展点", type = ExtensionType.PERFORM_MAIN)
public interface PerformAcceptOrderExtension extends BaseExtension {

    public PerformContext acceptOrder(PerformCmd cmd);

}