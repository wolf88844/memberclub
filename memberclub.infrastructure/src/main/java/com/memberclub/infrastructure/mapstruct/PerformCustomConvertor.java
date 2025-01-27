/**
 * @(#)ConvertorMethod.java, 十二月 24, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.google.common.collect.Lists;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.common.GrantTypeEnum;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderExtraDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderStatusEnum;
import com.memberclub.domain.dataobject.aftersale.ApplySkuInfoDO;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemFinanceInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.TaskContentDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentItemDO;
import com.memberclub.domain.entity.trade.MemberPerformItem;
import lombok.SneakyThrows;
import org.mapstruct.Named;

import java.util.List;

/**
 * author: 掘金五阳
 */
public class PerformCustomConvertor {


    @Named("toAftersaleOrderExtraDO")
    public String toAftersaleOrderExtraDO(AftersaleOrderExtraDO extra) {
        return JsonUtils.toJson(extra);
    }

    @Named("toApplySkuDetails")
    public String toApplySkuDetails(List<ApplySkuInfoDO> details) {
        return JsonUtils.toJson(details);
    }

    @Named("toAftersaleSourceInt")
    public int toAftersaleSourceInt(AftersaleSourceEnum source) {
        return source.getCode();
    }

    @Named("toAftersaleSourceEnum")
    public AftersaleSourceEnum toAftersaleSourceEnum(int source) {
        return AftersaleSourceEnum.findByCode(source);
    }

    @Named("toAftersaleOrderStatusInt")
    public int toAftersaleOrderStatusInt(AftersaleOrderStatusEnum status) {
        return status.getCode();
    }

    @Named("toAftersaleOrderStatusEnum")
    public AftersaleOrderStatusEnum toAftersaleOrderStatusEnum(int status) {
        return AftersaleOrderStatusEnum.findByCode(status);
    }

    @Named("toRefundTypeInt")
    public int toRefundTypeInt(RefundTypeEnum source) {
        return source.getCode();
    }

    @Named("toRefundTypeEnum")
    public RefundTypeEnum toRefundTypeEnum(int source) {
        return RefundTypeEnum.findByCode(source);
    }


    @Named("toPeriodTypeInt")
    public int toPeriodTypeInt(PeriodTypeEnum periodType) {
        return periodType.getCode();
    }

    @Named("toPeriodTypeEnum")
    public PeriodTypeEnum toPeriodTypeEnum(int periodType) {
        return PeriodTypeEnum.findByCode(periodType);
    }

    @Named("toRightTypeEnum")
    public RightTypeEnum toRightTypeEnum(int rightType) {
        return RightTypeEnum.findByCode(rightType);
    }

    @Named("toRightTypeInt")
    public int toRightTypeInt(RightTypeEnum rightType) {
        return rightType.getCode();
    }

    @Named("toGrantTypeInt")
    public int toGrantTypeInt(GrantTypeEnum grantTypeEnum) {
        return grantTypeEnum.getCode();
    }

    @Named("toGrantTypeEnum")
    public GrantTypeEnum toGrantTypeEnum(int grantType) {
        return GrantTypeEnum.findByCode(grantType);
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
    public String toSettleInfoString(PerformItemFinanceInfo settleInfo) {
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

    @Named("toPerformItemExtraInfo")
    public PerformItemExtraInfo toPerformItemExtraInfo(String extraInfo) {
        return JsonUtils.fromJson(extraInfo, PerformItemExtraInfo.class);
    }

    @Named("toPerformItemStatusEnumInt")
    public int toPerformItemStatusEnumInt(PerformItemStatusEnum e) {
        return e.getCode();
    }

    @Named("toPerformItemStatusEnum")
    public PerformItemStatusEnum toPerformItemStatusEnum(int code) {
        return PerformItemStatusEnum.findByCode(code);
    }


    @SneakyThrows
    public static TaskContentDO toTaskContentDO(String content, String className) {
        Class<?> clazz = Class.forName(className);
        return (TaskContentDO) JsonUtils.fromJson(content, clazz);
    }


    public static OnceTaskDO buildTaskForPeriodPerform(DelayItemContext itemContext, PerformContext context, MemberPerformItemDO item) {
        OnceTaskDO task = new OnceTaskDO();
        task.setUserId(context.getUserId());
        task.setBizType(context.getBizType());
        task.setStime(item.getStime());
        task.setEtime(item.getEtime());
        task.setStatus(OnceTaskStatusEnum.INIT);
        task.setTaskGroupId(String.valueOf(itemContext.getSubOrderPerformContext().getSubOrder().getSubTradeId()));
        task.setTaskToken(String.format("%s", item.getItemToken()));
        task.setCtime(TimeUtil.now());
        task.setUtime(TimeUtil.now());
        return task;
    }


    public static PerformTaskContentDO buildPerformTaskContent(DelayItemContext context, List<MemberPerformItemDO> items) {
        PerformTaskContentDO content = new PerformTaskContentDO();
        content.setBizType(context.getPerformContext().getBizType().getCode());
        content.setSubTradeId(context.getSubOrderPerformContext().getSubOrder().getSubTradeId());
        content.setTradeId(context.getPerformContext().getTradeId());
        content.setSkuId(context.getSubOrderPerformContext().getSubOrder().getSkuId());
        content.setPhase(context.getPhase());

        List<PerformTaskContentItemDO> contentItems = Lists.newArrayList();
        for (MemberPerformItemDO item : items) {
            PerformTaskContentItemDO itemPO = PerformCustomConvertor.toContentItem(item,
                    context.getPerformContext(), context.getSubOrderPerformContext());
            contentItems.add(itemPO);
        }

        content.setItems(contentItems);
        return content;
    }

    public static PerformTaskContentItemDO toContentItem(MemberPerformItemDO item,
                                                         PerformContext context,
                                                         SubOrderPerformContext subOrderPerformContext) {
        PerformTaskContentItemDO itemPO = PerformConvertor.INSTANCE.toPerformTaskContentItemDO(item);
        itemPO.setBizType(context.getBizType().getCode());
        itemPO.setUserId(context.getUserId());
        itemPO.setTradeId(context.getTradeId());
        itemPO.setSkuId(subOrderPerformContext.getSubOrder().getSkuId());
        itemPO.setCtime(TimeUtil.now());
        itemPO.setUtime(TimeUtil.now());
        return itemPO;
    }

    public static MemberPerformItem toMemberPerformItem(MemberPerformItemDO item,
                                                        PerformItemContext context) {
        MemberPerformItem itemPO = PerformConvertor.INSTANCE.toMemberPerformItem(item);
        itemPO.setBizType(context.getBizType().getCode());
        itemPO.setUserId(context.getUserId());
        itemPO.setTradeId(context.getTradeId());
        itemPO.setSkuId(context.getSkuId());
        itemPO.setCtime(TimeUtil.now());
        itemPO.setUtime(TimeUtil.now());
        return itemPO;
    }
}