/**
 * @(#)MQEventEnum.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

/**
 * @author yuhaiqiang
 */
public enum MQTopicEnum {

    TRADE_EVENT(1, MQContants.TRADE_EVENT_TOPIC),
    ;

    private int code;

    private String name;

    MQTopicEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static MQTopicEnum findByCode(int code) throws IllegalArgumentException {
        for (MQTopicEnum item : MQTopicEnum.values()) {
            if (item.code == code) {
                return item;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return this.name;
    }

    public int getCode() {
        return this.code;
    }
}
