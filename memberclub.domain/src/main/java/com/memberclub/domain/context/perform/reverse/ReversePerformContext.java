/**
 * @(#)ReverseContext.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.reverse;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import lombok.Data;

import java.util.List;

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

    private List<SubOrderReverseInfo> reverseInfos;
    /**************************************/
    //临时数据

    private SubOrderReverseInfo currentSubOrderReverseInfo;

    /**************************************/

}