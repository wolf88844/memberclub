/**
 * @(#)VersionCacheCmd.java, 二月 03, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.cache;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class VersionCacheCmd {

    private String key;

    private String versionKey;

    private long version;

    private String valueKey;

    private String value;
}