/**
 * @(#)DynamicConfig.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.dynamic_config;

/**
 * // TODO: 2024/12/14 接入分布式字典服务
 *
 * @author 掘金五阳
 */
public class DynamicConfig {
    public static boolean getBoolean(String key, Boolean value) {
        return value;
    }

    public static int getInt(String key, int value) {
        return value;
    }

    public static long getLong(String key, long value) {
        return value;
    }

    public static String getString(String key, String value) {
        return value;
    }
}