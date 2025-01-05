/**
 * @(#)EmptyDynamicConfig.java, 十二月 26, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.dynamic_config.impl;

import com.memberclub.infrastructure.dynamic_config.DynamicConfig;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.config", havingValue = "local", matchIfMissing = false)
@Service
public class LocalDynamicConfig implements DynamicConfig {

    @Override
    public boolean getBoolean(String key, Boolean value) {
        return value;
    }

    @Override
    public int getInt(String key, int value) {
        return value;
    }

    @Override
    public long getLong(String key, long value) {
        return value;
    }

    @Override
    public String getString(String key, String value) {
        return value;
    }
}