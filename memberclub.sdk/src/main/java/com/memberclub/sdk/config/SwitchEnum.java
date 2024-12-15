/**
 * @(#)SwitchEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.config;

import com.memberclub.infrastructure.dynamic_config.DynamicConfig;

/**
 * @author 掘金五阳
 */
public enum SwitchEnum {

    LOCK_TIMEOUT_SECONDS("lock_time", 10),
    PERFORM_RETRY_MAX_TIME("perform_retry_max_time", 5),

    ;


    private String key;

    private Object defaultValue;

    private int maxRate = 100;

    SwitchEnum(String key, Object defaultValue) {
        this(key, defaultValue, 100);
    }


    SwitchEnum(String key, Object defaultValue, Integer maxRate) {
        this.defaultValue = defaultValue;
        this.key = key;
        this.maxRate = maxRate;
    }

    private String getKey(int bizType) {
        return String.format("%s_%s", key, bizType);
    }

    private String getUserIdWhiteListKey() {
        return String.format("%s_%s", key, "white_list");
    }

    private String getRateKey() {
        return String.format("%s_%s", key, "rate");
    }

    public boolean getBoolean() {
        return DynamicConfig.getBoolean(key, (Boolean) defaultValue);
    }

    public boolean getBoolean(int bizType) {
        String key = getKey(bizType);

        return DynamicConfig.getBoolean(key, (Boolean) defaultValue);
    }

    public boolean isEnable(int bizType, long userId) {
        String rateKey = getRateKey();

        int rateConfig = DynamicConfig.getInt(rateKey, 0);

        int rate = (int) (userId % maxRate);

        if (rate < rateConfig) {
            return true;
        }

        String whiteList = DynamicConfig.getString(getUserIdWhiteListKey(), "");

        String[] whiteUserIds = whiteList.split(",");
        for (String whiteUserId : whiteUserIds) {
            if (whiteUserId.equals(String.valueOf(userId))) {
                return true;
            }
        }
        return false;
    }

    public int getInt() {
        return DynamicConfig.getInt(key, (int) defaultValue);
    }

    public int getInt(int bizType) {
        String key = getKey(bizType);
        return DynamicConfig.getInt(key, (int) defaultValue);
    }

    public long getLong() {
        return DynamicConfig.getLong(key, (long) defaultValue);
    }

    public long getLong(int bizType) {
        String key = getKey(bizType);
        return DynamicConfig.getLong(key, (long) defaultValue);
    }

    public String getString() {
        return DynamicConfig.getString(key, (String) defaultValue);
    }

    public String getString(int bizType) {
        String key = getKey(bizType);
        return DynamicConfig.getString(key, (String) defaultValue);
    }
}
