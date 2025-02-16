/**
 * @(#)QuotaExtension.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.quota.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;

/**
 * author: 掘金五阳
 */

@ExtensionConfig(desc = "用户配额扩展点", must = false, type = ExtensionType.PURCHASE)
public interface QuotaExtension extends BaseExtension {

    public void buildUserTagOp(QuotaExtensionContext context);
}