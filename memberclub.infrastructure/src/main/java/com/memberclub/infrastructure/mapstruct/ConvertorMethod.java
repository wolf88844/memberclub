/**
 * @(#)ConvertorMethod.java, 十二月 24, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.GrantTypeEnum;
import com.memberclub.domain.common.MemberPerformHisStatusEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.his.PerformHisExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSettleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import org.mapstruct.Named;

/**
 * author: 掘金五阳
 */
public class ConvertorMethod {


    @Named("toBizTypeInt")
    public int toBizTypeInt(BizTypeEnum bizType) {
        return bizType.toBizType();
    }

    @Named("toOrderSystemTypeInt")
    public int toOrderSystemTypeInt(OrderSystemTypeEnum orderSystemType) {
        return orderSystemType.toInt();
    }

    @Named("toMemberPerformHisStatusInt")
    public int toMemberPerformHisStatusInt(MemberPerformHisStatusEnum status) {
        return status.toInt();
    }

    @Named("toPeriodTypeInt")
    public int toPeriodTypeInt(PeriodTypeEnum periodType) {
        return periodType.toInt();
    }

    @Named("toPeriodTypeEnum")
    public PeriodTypeEnum toPeriodTypeEnum(int periodType) {
        return PeriodTypeEnum.findByInt(periodType);
    }

    @Named("toRightTypeEnum")
    public RightTypeEnum toRightTypeEnum(int rightType) {
        return RightTypeEnum.findByInt(rightType);
    }

    @Named("toRightTypeInt")
    public int toRightTypeInt(RightTypeEnum rightType) {
        return rightType.toInt();
    }

    @Named("toGrantTypeInt")
    public int toGrantTypeInt(GrantTypeEnum grantTypeEnum) {
        return grantTypeEnum.toInt();
    }

    @Named("toGrantTypeEnum")
    public GrantTypeEnum toGrantTypeEnum(int grantType) {
        return GrantTypeEnum.findByInt(grantType);
    }

    @Named("toGrantInfoString")
    public String toGrantInfoString(PerformItemGrantInfo grantInfo) {
        return JsonUtils.toJson(grantInfo);
    }

    @Named("toViewInfoString")
    public String toViewInfoString(PerformItemViewInfo viewInfo) {
        return JsonUtils.toJson(viewInfo);
    }

    @Named("toSettleInfoString")
    public String toSettleInfoString(PerformItemSettleInfo settleInfo) {
        return JsonUtils.toJson(settleInfo);
    }

    @Named("toSaleInfoString")
    public String toSaleInfoString(PerformItemSaleInfo saleInfo) {
        return JsonUtils.toJson(saleInfo);
    }

    @Named("toExtraInfoString")
    public String toExtraInfoString(PerformItemExtraInfo extraInfo) {
        return JsonUtils.toJson(extraInfo);
    }

    @Named("toMemberPerformHisExtraString")
    public String toMemberPerformHisExtraString(PerformHisExtraInfo extraInfo) {
        return JsonUtils.toJson(extraInfo);
    }
}