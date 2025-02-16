/**
 * @(#)ReverseContext.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.reverse;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import lombok.Data;

import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class ReversePerformContext {

    private BizTypeEnum bizType;

    private long userId;

    private String tradeId;

    private MemberOrderDO memberOrderDO;

    private AfterSaleApplyContext afterSaleApplyContext;

    private Map<String, SubOrderReversePerformContext> subTradeId2SubOrderReversePerformContext;
    /**************************************/
    //临时数据

    private SubOrderReversePerformContext currentSubOrderReversePerformContext;

    /**************************************/

}