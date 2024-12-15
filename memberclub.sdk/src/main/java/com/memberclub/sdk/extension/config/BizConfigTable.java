/**
 * @(#)BizConfigTable.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.config;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.sdk.common.LockMode;

/**
 * author: 掘金五阳
 */
public interface BizConfigTable extends BaseExtension {

    default LockMode getLockMode() {
        return LockMode.LOCK_ORDER;
    }
}