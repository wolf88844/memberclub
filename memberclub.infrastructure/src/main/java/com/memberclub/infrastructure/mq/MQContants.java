/**
 * @(#)MQContants.java, 一月 14, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

/**
 * author: 掘金五阳
 */
public class MQContants {

    public static final String TRADE_EVENT_TOPIC = "com.memberclub.trade.event";

    public static final String PRE_FINANCE_EVENT_TOPIC = "com.memberclub.prefinance.event";

    public static final String TRADE_EVENT_QUEUE_ON_PRE_FINANCE = "com.memberclub.trade.event.consumer.prefinance";
}