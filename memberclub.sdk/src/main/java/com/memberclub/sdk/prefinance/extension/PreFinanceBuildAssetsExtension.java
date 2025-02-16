/**
 * @(#)PreFinanceBuildAssetsExtension.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.prefinance.PreFinanceContext;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "结算构建资产扩展点", type = ExtensionType.PRE_FINANCE, must = false)
public interface PreFinanceBuildAssetsExtension extends BaseExtension {

    public boolean buildAssets(PreFinanceContext context);

    public void buildOnPerform(PreFinanceContext context);

    public void buildOnFreeze(PreFinanceContext context);

    public void buildOnExpire(PreFinanceContext context);

    public void buildOnRefund(PreFinanceContext context);
}