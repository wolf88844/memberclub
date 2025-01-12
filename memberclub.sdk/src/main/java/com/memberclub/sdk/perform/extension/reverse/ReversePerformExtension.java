/**
 * @(#)ReversePerformExtension.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.reverse;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;

/**
 * @author 掘金五阳
 */
@ExtensionConfig(desc = "逆向履约流程层扩展点", type = ExtensionType.REVERSE_PERFORM, must = true)
public interface ReversePerformExtension extends BaseExtension {

    public void reverse(ReversePerformContext context);
}