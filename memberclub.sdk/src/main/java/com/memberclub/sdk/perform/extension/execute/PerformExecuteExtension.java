/**
 * @(#)PerformExecuteExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.execute;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.PerformContext;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "履约执行主流程 扩展点", type = ExtensionType.PERFORM_MAIN)
public interface PerformExecuteExtension extends BaseExtension {

    public void execute(PerformContext context);
}