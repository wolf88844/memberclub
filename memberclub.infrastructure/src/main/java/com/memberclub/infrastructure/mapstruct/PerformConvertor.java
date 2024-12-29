/**
 * @(#)PerformConvertor.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.context.aftersale.apply.AftersaleApplyCmd;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformHisDO;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.his.PerformHisSaleInfo;
import com.memberclub.domain.dataobject.perform.his.PerformHisSettleInfo;
import com.memberclub.domain.dataobject.perform.his.PerformHisViewInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemGrantInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSaleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemSettleInfo;
import com.memberclub.domain.dataobject.perform.item.PerformItemViewInfo;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.dataobject.sku.SkuSaleInfo;
import com.memberclub.domain.dataobject.sku.SkuSettleInfo;
import com.memberclub.domain.dataobject.sku.SkuViewInfo;
import com.memberclub.domain.dataobject.sku.rights.RightGrantInfo;
import com.memberclub.domain.dataobject.sku.rights.RightSaleInfo;
import com.memberclub.domain.dataobject.sku.rights.RightSettleInfo;
import com.memberclub.domain.dataobject.sku.rights.RightViewInfo;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
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
@Mapper(uses = ConvertorMethod.class)
public interface PerformConvertor {

    PerformConvertor INSTANCE = Mappers.getMapper(PerformConvertor.class);

    public MemberSkuSnapshotDO toMemberSkuDO(MemberSkuDTO dto);

    public PerformContext toPerformContext(PerformCmd cmd);

    public MemberPerformHisDO toMemberPerformHisDO(PerformContext context);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toRightTypeEnum", target = "rightType"),
            @Mapping(target = "periodType", source = "periodType", qualifiedByName = "toPeriodTypeEnum"),
            @Mapping(target = "extra.grantInfo", source = "grantInfo"),
            @Mapping(target = "extra.viewInfo", source = "viewInfo"),
            @Mapping(target = "extra.saleInfo", source = "saleInfo"),
            @Mapping(target = "extra.settleInfo", source = "settleInfo"),

    })
    public MemberPerformItemDO toPerformItem(SkuPerformItemConfigDO performConfigDO);

    @Mappings(value = {
            @Mapping(qualifiedByName = "toRightTypeInt", target = "rightType"),
            @Mapping(qualifiedByName = "toGrantTypeInt", target = "grantType"),
            @Mapping(qualifiedByName = "toExtraInfoString", target = "extra"),
    })
    public MemberPerformItem toMemberPerformItem(MemberPerformItemDO item);


    public MemberPerformItemDO copyPerformItem(MemberPerformItemDO memberPerformItemDO);


    @Mappings(value = {
            @Mapping(qualifiedByName = "toBizTypeInt", target = "bizType"),
            @Mapping(qualifiedByName = "toOrderSystemTypeInt", target = "orderSystemType"),
            @Mapping(qualifiedByName = "toMemberPerformHisExtraString", target = "extra"),
            @Mapping(qualifiedByName = "toMemberPerformHisStatusInt", target = "status"),
    })
    public MemberPerformHis toMemberPerformHis(MemberPerformHisDO his);


    public AfterSalePreviewCmd toPreviewCmd(AftersaleApplyCmd cmd);

    public AftersaleApplyCmd toApplyCmd(AfterSalePreviewCmd cmd);

    public PerformItemViewInfo toViewInfo(RightViewInfo viewInfo);

    public PerformItemSettleInfo toSettleInfo(RightSettleInfo settleInfo);

    public PerformItemSaleInfo toSaleInfo(RightSaleInfo saleInfo);

    public PerformItemGrantInfo toGrantInfo(RightGrantInfo grantInfo);

    public PerformHisViewInfo toPerformHisViewInfo(SkuViewInfo viewInfo);

    public PerformHisSettleInfo toPerformHisSettleInfo(SkuSettleInfo settleInfo);

    public PerformHisSaleInfo toPerformHisSaleInfo(SkuSaleInfo saleInfo);
}