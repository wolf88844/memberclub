/**
 * @(#)SwitchEnum.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.common;

import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.dynamic_config.DynamicConfig;
import org.springframework.util.Assert;

/**
 * @author 掘金五阳
 */
public enum SwitchEnum {

    LOCK_TIMEOUT_SECONDS("lock_time", 10),
    PERFORM_RETRY_MAX_TIME("perform_retry_max_time", 5),
    AFTERSALE_DEGRADE("aftersale_degrade_%s", false),
    AFTERSALE_PLAN_GENERATE_DIGEST_VERSION("aftersale_plan_generate_digest_version", 1),
    AFTERSALE_PLAN_GENERATE_DIGEST_CHECK_DEGRADE("aftersale_plan_generate_digest_check_degrade", false),
    TRADE_EVENT_FOR_PRE_FINANCE_RETRY_TIMES("com.memberclub.trade.event.consumer.prefinance.retryTimes", 3),
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
        return getDynamicConfig().getBoolean(key, (Boolean) defaultValue);
    }

    public boolean getBoolean(int bizType, String... args) {
        String formatKey = String.format(key, args);
        return getBoolean(formatKey, bizType);
    }

    public boolean getBoolean(int bizType) {
        return getBoolean(key, bizType);
    }

    private boolean getBoolean(String key, int bizType) {
        key = getKey(bizType);

        return getDynamicConfig().getBoolean(key, (Boolean) defaultValue);
    }


    public boolean isEnable(int bizType, long userId) {
        String rateKey = getRateKey();

        int rateConfig = getDynamicConfig().getInt(rateKey, 0);

        int rate = (int) (userId % maxRate);

        if (rate < rateConfig) {
            return true;
        }

        String whiteList = getDynamicConfig().getString(getUserIdWhiteListKey(), "");

        String[] whiteUserIds = whiteList.split(",");
        for (String whiteUserId : whiteUserIds) {
            if (whiteUserId.equals(String.valueOf(userId))) {
                return true;
            }
        }
        return false;
    }

    public int getInt() {
        return getDynamicConfig().getInt(key, (int) defaultValue);
    }

    public int getInt(int bizType) {
        String key = getKey(bizType);
        return getDynamicConfig()
                .getInt(key, (int) defaultValue);
    }

    public long getLong() {
        return getDynamicConfig()
                .getLong(key, (long) defaultValue);
    }

    public long getLong(int bizType) {
        String key = getKey(bizType);
        return getDynamicConfig().getLong(key, (long) defaultValue);
    }

    public String getString() {
        return getDynamicConfig().getString(key, (String) defaultValue);
    }

    public String getString(int bizType) {
        String key = getKey(bizType);
        return getDynamicConfig().getString(key, (String) defaultValue);
    }

    public DynamicConfig getDynamicConfig() {
        DynamicConfig config = ApplicationContextUtils.getContext().getBean(DynamicConfig.class);
        Assert.notNull(config, "DynamicConfig依赖 Spring 启动");
        return config;
    }
}
