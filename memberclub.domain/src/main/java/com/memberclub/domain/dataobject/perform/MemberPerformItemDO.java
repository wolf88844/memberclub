/**
 * @(#)PerformItemDO.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.common.GrantTypeEnum;
import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSettleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberPerformItemDO {

    private long skuId;

    private long rightId;

    private RightTypeEnum rightType;

    private String itemToken;

    private String batchCode;

    private int assetCount;

    private int phase = 1;

    private int cycle;

    private int buyIndex = 1;

    /***
     * 0 发放
     * 1 激活
     */
    private GrantTypeEnum grantType;

    private String providerId;

    private PerformItemGrantInfo grantInfo = new PerformItemGrantInfo();

    private PerformItemViewInfo viewInfo = new PerformItemViewInfo();

    private PerformItemSettleInfo settleInfo = new PerformItemSettleInfo();

    private PerformItemSaleInfo saleInfo = new PerformItemSaleInfo();

    private PerformItemExtraInfo extra = new PerformItemExtraInfo();

    private int periodCount;

    private PeriodTypeEnum periodType;

    private long stime;

    private long etime;
}