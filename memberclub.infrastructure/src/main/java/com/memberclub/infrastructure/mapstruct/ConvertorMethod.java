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
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.GrantTypeEnum;
import com.memberclub.domain.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.common.RightTypeEnum;
import com.memberclub.domain.common.TaskTypeEnum;
import com.memberclub.domain.common.status.OnceTaskStatusEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderExtraDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderStatusEnum;
import com.memberclub.domain.dataobject.aftersale.ApplySkuDetail;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSettleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.TaskContentDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import lombok.SneakyThrows;
import org.mapstruct.Named;

import java.util.List;

/**
 * author: 掘金五阳
 */
public class ConvertorMethod {


    @Named("toAftersaleOrderExtraDO")
    public String toAftersaleOrderExtraDO(AftersaleOrderExtraDO extra) {
        return JsonUtils.toJson(extra);
    }

    @Named("toApplySkuDetails")
    public String toApplySkuDetails(List<ApplySkuDetail> details) {
        return JsonUtils.toJson(details);
    }

    @Named("toAftersaleSourceInt")
    public int toAftersaleSourceInt(AftersaleSourceEnum source) {
        return source.toInt();
    }

    @Named("toAftersaleSourceEnum")
    public AftersaleSourceEnum toAftersaleSourceEnum(int source) {
        return AftersaleSourceEnum.findByInt(source);
    }

    @Named("toAftersaleOrderStatusInt")
    public int toAftersaleOrderStatusInt(AftersaleOrderStatusEnum status) {
        return status.toInt();
    }

    @Named("toAftersaleOrderStatusEnum")
    public AftersaleOrderStatusEnum toAftersaleOrderStatusEnum(int status) {
        return AftersaleOrderStatusEnum.findByInt(status);
    }

    @Named("toRefundTypeInt")
    public int toRefundTypeInt(RefundTypeEnum source) {
        return source.toInt();
    }

    @Named("toRefundTypeEnum")
    public RefundTypeEnum toRefundTypeEnum(int source) {
        return RefundTypeEnum.findByInt(source);
    }

    @Named("toBizTypeInt")
    public int toBizTypeInt(BizTypeEnum bizType) {
        return bizType.toBizType();
    }

    @Named("toBizTypeEnum")
    public BizTypeEnum toBizType(int bizType) {
        return BizTypeEnum.findByInt(bizType);
    }


    @Named("toOrderSystemTypeInt")
    public int toOrderSystemTypeInt(OrderSystemTypeEnum orderSystemType) {
        return orderSystemType.toInt();
    }

    @Named("toMemberSubOrderStatusInt")
    public int toMemberSubOrderStatusInt(SubOrderPerformStatusEnum status) {
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

    @Named("toPerformItemExtraInfo")
    public PerformItemExtraInfo toPerformItemExtraInfo(String extraInfo) {
        return JsonUtils.fromJson(extraInfo, PerformItemExtraInfo.class);
    }

    @Named("toMemberSubOrderExtraString")
    public String toMemberSubOrderExtraString(SubOrderExtraInfo extraInfo) {
        return JsonUtils.toJson(extraInfo);
    }

    @Named("toTaskStatusInt")
    public int toTaskStatusInt(OnceTaskStatusEnum status) {
        return status.toInt();
    }

    @Named("toTaskStatusEnum")
    public OnceTaskStatusEnum toTaskStatusEnum(int status) {
        return OnceTaskStatusEnum.findByInt(status);
    }

    @SneakyThrows
    public static TaskContentDO toTaskContentDO(String content, String className) {
        Class<?> clazz = Class.forName(className);
        return (TaskContentDO) JsonUtils.fromJson(content, clazz);
    }


    @Named("toTaskTypeInt")
    public int toTaskTypeInt(TaskTypeEnum taskType) {
        return taskType.toInt();
    }

    @Named("toTaskTypeEnum")
    public TaskTypeEnum toTaskTypeEnum(int taskType) {
        return TaskTypeEnum.findByInt(taskType);
    }

    @Named("toTaskContentString")
    public String toTaskContentString(TaskContentDO content) {
        return JsonUtils.toJson(content);
    }

    public static OnceTaskDO buildTaskForPeriodPerform(PerformContext context, MemberPerformItemDO item) {
        OnceTaskDO task = new OnceTaskDO();
        task.setUserId(context.getUserId());
        task.setBizType(context.getBizType());
        task.setStime(item.getStime());
        task.setEtime(item.getEtime());
        task.setStatus(OnceTaskStatusEnum.INIT);
        task.setTaskToken(String.format("%s", item.getItemToken()));
        task.setCtime(TimeUtil.now());
        task.setUtime(TimeUtil.now());
        return task;
    }


    public static PerformTaskContentDO buildPerformTaskContent(DelayItemContext context, List<MemberPerformItemDO> items) {
        PerformTaskContentDO content = new PerformTaskContentDO();
        content.setBizType(context.getPerformContext().getBizType().toBizType());
        content.setSubOrderToken(context.getSkuPerformContext().getHis().getSubOrderToken());
        content.setTradeId(context.getPerformContext().getTradeId());
        content.setSkuId(context.getSkuPerformContext().getHis().getSkuId());

        List<PerformTaskContentItemDO> contentItems = Lists.newArrayList();
        for (MemberPerformItemDO item : items) {
            PerformTaskContentItemDO itemPO = ConvertorMethod.toContentItem(item,
                    context.getPerformContext(), context.getSkuPerformContext());
            contentItems.add(itemPO);
        }

        content.setItems(contentItems);
        return content;
    }

    public static PerformTaskContentItemDO toContentItem(MemberPerformItemDO item,
                                                         PerformContext context,
                                                         SkuPerformContext skuPerformContext) {
        PerformTaskContentItemDO itemPO = PerformConvertor.INSTANCE.toPerformTaskContentItemDO(item);
        itemPO.setBizType(context.getBizType().toBizType());
        itemPO.setUserId(context.getUserId());
        itemPO.setTradeId(context.getTradeId());
        itemPO.setSkuId(skuPerformContext.getHis().getSkuId());
        itemPO.setCtime(TimeUtil.now());
        itemPO.setUtime(TimeUtil.now());
        itemPO.setStatus(PerformItemStatusEnum.INIT.toInt());
        return itemPO;
    }

    public static MemberPerformItem toMemberPerformItem(MemberPerformItemDO item,
                                                        PerformItemContext context) {
        MemberPerformItem itemPO = PerformConvertor.INSTANCE.toMemberPerformItem(item);
        itemPO.setBizType(context.getBizType().toBizType());
        itemPO.setUserId(context.getUserId());
        itemPO.setTradeId(context.getTradeId());
        itemPO.setSkuId(context.getSkuId());
        itemPO.setCtime(TimeUtil.now());
        itemPO.setUtime(TimeUtil.now());
        itemPO.setStatus(PerformItemStatusEnum.INIT.toInt());
        return itemPO;
    }
}