/**
 * @(#)CommonConvertor.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.dataobject.event.trade.TradeEventDetail;
import com.memberclub.domain.dataobject.event.trade.TradeEventDetailDO;
import com.memberclub.infrastructure.mapstruct.custom.CommonCustomConvertor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * author: 掘金五阳
 */
@Mapper(uses = {CommonCustomConvertor.class})
public interface CommonConvertor {
    CommonConvertor INSTANCE = Mappers.getMapper(CommonConvertor.class);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toBizTypeInt", target = "bizType"),
            @Mapping(qualifiedByName = "toSubOrderPerformStatusEnumInt", target = "performStatus"),

    })
    public TradeEventDetail toTradeEvent(TradeEventDetailDO detail);


    @Mappings(value = {
            @Mapping(qualifiedByName = "toBizTypeEnum", target = "bizType"),
            @Mapping(qualifiedByName = "toSubOrderPerformStatusEnum", target = "performStatus"),

    })
    public TradeEventDetailDO toTradeEventDetailDO(TradeEventDetail detail);

}