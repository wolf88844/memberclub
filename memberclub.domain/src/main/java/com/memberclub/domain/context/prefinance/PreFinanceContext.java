/**
 * @(#)PreFinanceContext.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance;

import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.prefinance.FinanceEventEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceContext {

    private TradeEventDO event;

    private FinanceEventEnum financeEventEnum;
}