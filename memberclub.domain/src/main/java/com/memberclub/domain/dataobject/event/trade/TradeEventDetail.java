/**
 * @(#)TradeEventDetail.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.event.trade;

import lombok.Data;

import java.util.List;

/**
 * @see com.memberclub.domain.dataobject.event.trade.TradeEventDetailDO
 * author: 掘金五阳
 */
@Data
public class TradeEventDetail {

    private int bizType;

    private Long userId;

    private String tradeId;

    private Long subTradeId;

    private Long skuId;

    private Long eventTime;

    private Integer periodIndex;

    private List<String> itemTokens;

    private int performStatus;
}