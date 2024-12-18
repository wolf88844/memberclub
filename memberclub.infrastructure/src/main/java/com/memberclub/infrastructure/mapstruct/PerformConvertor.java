/**
 * @(#)PerformConvertor.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.dto.sku.MemberSkuDTO;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.entity.MemberPerformItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 掘金五阳
 */
@Mapper
public interface PerformConvertor {

    PerformConvertor INSTANCE = Mappers.getMapper(PerformConvertor.class);

    public MemberSkuSnapshotDO toMemberSkuDO(MemberSkuDTO dto);

    public PerformContext toPerformContext(PerformCmd cmd);

    public SkuPerformContext toSkuPerformContext(SkuBuyDetailDO skuBuyDetailDO);

    public PerformItemDO toPerformItem(SkuPerformItemConfigDO performConfigDO);

    public PerformItemDO copyPerformItem(PerformItemDO performItemDO);

    @Mappings(value = {
            @Mapping(expression = "java(context.getBizType().toBizType())", target = "bizType"),
            @Mapping(source = "context.userId", target = "userId"),
            @Mapping(expression = "java(context.getOrderSystemType().toInt())", target = "orderSystemType"),
            @Mapping(source = "context.orderId", target = "orderId"),
            @Mapping(source = "context.tradeId", target = "tradeId"),
            @Mapping(source = "skuPerformContext.buyCount", target = "buyCount"),
            @Mapping(source = "skuPerformContext.skuId", target = "skuId"),
    })
    public MemberPerformHis toMemberPerformHis(PerformContext context, SkuPerformContext skuPerformContext);
    
    public MemberPerformItem toMemberPerformItem(PerformItemDO item);
}