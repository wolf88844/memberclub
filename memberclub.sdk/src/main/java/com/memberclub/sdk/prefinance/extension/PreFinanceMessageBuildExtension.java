/**
 * @(#)PreFinanceMessageBuildExtension.java, 一月 26, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.context.prefinance.PreFinanceContext;
import com.memberclub.domain.context.prefinance.PreFinanceEvent;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "预结算消息构建扩展点", must = false, type = ExtensionType.PRE_FINANCE)
public interface PreFinanceMessageBuildExtension extends BaseExtension {

    default String buildMessage(PreFinanceContext context, PreFinanceEvent event) {
        return JsonUtils.toJson(event);
    }
}