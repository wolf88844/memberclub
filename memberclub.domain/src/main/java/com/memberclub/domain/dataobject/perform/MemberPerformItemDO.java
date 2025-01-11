/**
 * @(#)PerformItemDO.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.common.GrantTypeEnum;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberPerformItemDO implements Comparable {

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

    private int providerId;

    private PerformItemExtraInfo extra = new PerformItemExtraInfo();

    private int periodCount;

    private PeriodTypeEnum periodType;

    private long stime;

    private long etime;

    private long utime;

    PerformItemStatusEnum status;


    @Override
    public int compareTo(Object o) {
        return phase < ((MemberPerformItemDO) o).getPhase() ? -1 : 1;
    }

    public void onFinishPerform(PerformItemContext context) {
        status = PerformItemStatusEnum.PERFORM_SUCCESS;
        utime = System.currentTimeMillis();
    }
}