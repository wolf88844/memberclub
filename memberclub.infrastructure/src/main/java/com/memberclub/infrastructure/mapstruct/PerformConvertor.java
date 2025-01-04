/**
 * @(#)PerformConvertor.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSettleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.dataobject.sku.rights.RightGrantInfo;
import com.memberclub.domain.dataobject.sku.rights.RightSaleInfo;
import com.memberclub.domain.dataobject.sku.rights.RightSettleInfo;
import com.memberclub.domain.dataobject.sku.rights.RightViewInfo;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentItemDO;
import com.memberclub.domain.dto.sku.MemberSkuDTO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.infrastructure.mapstruct.custom.CommonCustomConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 掘金五阳
 */
@Mapper(uses = {PerformCustomConvertor.class, CommonCustomConvertor.class})
public interface PerformConvertor {

    PerformConvertor INSTANCE = Mappers.getMapper(PerformConvertor.class);

    public SkuInfoDO toMemberSkuInfoDO(MemberSkuDTO dto);

    public PerformContext toPerformContext(PerformCmd cmd);

    public MemberSubOrderDO toSubOrderDO(PerformContext context);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toRightTypeEnum", target = "rightType"),
            @Mapping(target = "periodType", source = "periodType", qualifiedByName = "toPeriodTypeEnum"),
            @Mapping(target = "extra.grantInfo", source = "grantInfo"),
            @Mapping(target = "extra.viewInfo", source = "viewInfo"),
            @Mapping(target = "extra.saleInfo", source = "saleInfo"),
            @Mapping(target = "extra.settleInfo", source = "settleInfo"),

    })
    public MemberPerformItemDO toPerformItem(SkuPerformItemConfigDO performConfigDO);

    @Mappings({
            @Mapping(qualifiedByName = "toRightTypeEnum", target = "rightType"),
            @Mapping(qualifiedByName = "toGrantTypeEnum", target = "grantType"),
            @Mapping(qualifiedByName = "toPerformItemExtraInfo", target = "extra"),
            @Mapping(qualifiedByName = "toPeriodTypeEnum", target = "periodType"),

    })
    public MemberPerformItemDO toMemberPerformItemDOForPeriodPerform(PerformTaskContentItemDO item);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toRightTypeInt", target = "rightType"),
            @Mapping(qualifiedByName = "toGrantTypeInt", target = "grantType"),
            @Mapping(qualifiedByName = "toExtraInfoString", target = "extra"),
    })
    public MemberPerformItem toMemberPerformItem(MemberPerformItemDO item);

    public PerformItemContext toPerformItemContextForPeriodPerform(PeriodPerformContext context);


    public PeriodPerformContext toPeriodPerformContextForTask(OnceTaskDO task);

    @Mappings({
            @Mapping(target = "bizType", qualifiedByName = "toBizTypeEnum"),
            @Mapping(target = "taskType", qualifiedByName = "toTaskTypeEnum"),
            @Mapping(target = "status", qualifiedByName = "toTaskStatusEnum"),
            @Mapping(target = "content", ignore = true)
    })
    public OnceTaskDO toOnceTaskDO(OnceTask onceTask);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toRightTypeInt", target = "rightType"),
            @Mapping(qualifiedByName = "toGrantTypeInt", target = "grantType"),
            @Mapping(qualifiedByName = "toExtraInfoString", target = "extra"),
    })
    public PerformTaskContentItemDO toPerformTaskContentItemDO(MemberPerformItemDO item);


    public MemberPerformItemDO copyPerformItem(MemberPerformItemDO memberPerformItemDO);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toBizTypeInt", target = "bizType"),
            @Mapping(qualifiedByName = "toTaskStatusInt", target = "status"),
            @Mapping(qualifiedByName = "toTaskTypeInt", target = "taskType"),
            @Mapping(qualifiedByName = "toTaskContentString", target = "content"),
    })
    public OnceTask toOnceTask(OnceTaskDO task);


    public PerformItemViewInfo toViewInfo(RightViewInfo viewInfo);

    public PerformItemSettleInfo toSettleInfo(RightSettleInfo settleInfo);

    public PerformItemSaleInfo toSaleInfo(RightSaleInfo saleInfo);

    public PerformItemGrantInfo toGrantInfo(RightGrantInfo grantInfo);
}