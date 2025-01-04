/**
 * @(#)SkuReversePerformInfo.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.reverse;

import com.memberclub.domain.entity.MemberSubOrder;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SubOrderReverseInfo {

    private long skuId;

    private Long subTradeId;

    private MemberSubOrder memberSubOrder;

    private List<PerformItemReverseInfo> items;

    private boolean allRefund;

    //如果有待取消的任务,也一并取消掉!


    /************************************/
    //临时数据
    private List<PerformItemReverseInfo> currentItems;

    private int currentRightType;

    /************************************/
}