/**
 * @(#)MQQueueEnum.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

import lombok.Getter;

/**
 * @author yuhaiqiang
 */
public enum MQQueueEnum {

    TRADE_EVENT_FOR_PRE_FINANCE(1,
            MQContants.TRADE_EVENT_QUEUE_ON_PRE_FINANCE,
            MQContants.TRADE_EVENT_TOPIC,
            5000),
    ;

    private int code;

    private String queneName;

    private String topicName;

    @Getter
    private long delayMillSeconds;

    MQQueueEnum(int code, String name, String topicName,
                long delayMillSeconds) {
        this.code = code;
        this.queneName = name;
        this.topicName = topicName;
        this.delayMillSeconds = delayMillSeconds;
    }

    public static MQQueueEnum findByCode(int code) throws IllegalArgumentException {
        for (MQQueueEnum item : MQQueueEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.queneName;
    }

    public int getCode() {
        return this.code;
    }

    public String getQueneName() {
        return queneName;
    }

    public String getDelayQueneName() {
        return queneName + "_delay";
    }

    public MQQueueEnum setQueneName(String queneName) {
        this.queneName = queneName;
        return this;
    }

    public String getTopicName() {
        return topicName;
    }

    public MQQueueEnum setTopicName(String topicName) {
        this.topicName = topicName;
        return this;
    }
}
