/**
 * @(#)PerformDataObjectBuildFactory.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.context.perform.common.GrantTypeEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.item.PerformItemExtraInfo;
import com.memberclub.domain.entity.MemberPerformItem;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class PerformDataObjectBuildFactory {

    public List<MemberPerformItemDO> toMemberPerformItemDOs(List<MemberPerformItem> items) {
        return items.stream().map(this::toMemberPerformItemDO).collect(Collectors.toList());
    }

    public MemberPerformItemDO toMemberPerformItemDO(MemberPerformItem item) {
        MemberPerformItemDO itemDO = new MemberPerformItemDO();
        itemDO.setAssetCount(item.getAssetCount());
        itemDO.setBatchCode(item.getBatchCode());
        itemDO.setBuyIndex(item.getBuyIndex());
        itemDO.setCycle(item.getCycle());
        itemDO.setEtime(item.getEtime());
        itemDO.setExtra(JsonUtils.fromJson(item.getExtra(), PerformItemExtraInfo.class));
        itemDO.setGrantType(GrantTypeEnum.findByCode(item.getGrantType()));
        itemDO.setItemToken(item.getItemToken());
        itemDO.setPeriodCount(itemDO.getExtra().getGrantInfo().getPeriodCount());
        itemDO.setPeriodType(PeriodTypeEnum.findByCode(itemDO.getExtra().getGrantInfo().getPeriodType()));
        itemDO.setPhase(item.getPhase());
        itemDO.setProviderId(item.getProviderId());
        itemDO.setRightId(item.getRightId());
        itemDO.setRightType(RightTypeEnum.findByCode(item.getRightType()));
        itemDO.setSkuId(item.getSkuId());
        itemDO.setStime(item.getStime());
        return itemDO;
    }

}