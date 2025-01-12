/**
 * @(#)TradeEventDetailDO.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.event.trade;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class TradeEventDetailDO {


    private Long userId;

    private BizTypeEnum bizType;

    private String tradeId;

    private Long subTradeId;

    private Long skuId;

    private Long eventTime;

    private SubOrderPerformStatusEnum performStatus;
}