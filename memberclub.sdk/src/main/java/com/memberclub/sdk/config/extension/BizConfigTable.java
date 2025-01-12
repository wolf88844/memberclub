/**
 * @(#)BizConfigTable.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.config.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.sdk.common.LockMode;

/**
 * author: 掘金五阳
 */

@ExtensionConfig(desc = "通用配置扩展点", type = ExtensionType.COMMON)
public interface BizConfigTable extends BaseExtension {

    default LockMode getLockMode() {
        return LockMode.LOCK_ORDER;
    }
}