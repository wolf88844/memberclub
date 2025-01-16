/**
 * @(#)MQQueueEnum.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

/**
 * @author yuhaiqiang
 */
public enum MQQueueEnum {

    TRADE_EVENT_FOR_PRE_FINANCE(1, MQContants.TRADE_EVENT_FOR_PRE_FINANCE, MQContants.TRADE_EVENT_TOPIC);

    private int code;

    private String queneName;

    private String topicName;

    MQQueueEnum(int code, String name, String topicName) {
        this.code = code;
        this.queneName = name;
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
