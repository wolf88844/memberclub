/**
 * @(#)AftersaleConvertor.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.context.aftersale.apply.AftersaleApplyCmd;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.domain.entity.AftersaleOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * author: 掘金五阳
 */
@Mapper(uses = ConvertorMethod.class)
public interface AftersaleConvertor {

    AftersaleConvertor INSTANCE = Mappers.getMapper(AftersaleConvertor.class);

    public AftersaleOrderDO toAftersaleOrderDO(AftersaleApplyCmd cmd);

    @Mappings({
            @Mapping(target = "bizType", qualifiedByName = "toBizTypeInt"),
            @Mapping(target = "source", qualifiedByName = "toAftersaleSourceInt"),
            @Mapping(target = "status", qualifiedByName = "toAftersaleOrderStatusInt"),
            @Mapping(target = "refundType", qualifiedByName = "toRefundTypeInt"),
            @Mapping(target = "extra", qualifiedByName = "toAftersaleOrderExtraDO"),
            @Mapping(target = "applySkuDetails", qualifiedByName = "toApplySkuDetails"),
    })
    public AftersaleOrder toAftersaleOrder(AftersaleOrderDO orderDO);

    public AftersaleApplyCmd toAftersaleApplyCmd(AfterSalePreviewCmd previewCmd);

    public ReversePerformContext toReversePerformContext(AftersaleApplyCmd cmd);
}