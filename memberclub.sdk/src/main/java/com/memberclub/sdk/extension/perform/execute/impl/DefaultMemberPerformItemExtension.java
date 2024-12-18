/**
 * @(#)DefaultMemberPerformItemExtension.java, 十二月 16, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute.impl;

import com.google.common.collect.Lists;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.extension.perform.execute.MemberPerformItemExtension;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "构建履约项扩展点,默认实现", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultMemberPerformItemExtension implements MemberPerformItemExtension {

    @Override
    public List<MemberPerformItem> toMemberPerformItems(PerformItemContext performItemContext) {
        List<MemberPerformItem> items = Lists.newArrayList();
        for (PerformItemDO item : performItemContext.getItems()) {
            MemberPerformItem itemPO = PerformConvertor.INSTANCE.toMemberPerformItem(item);
            itemPO.setBizType(performItemContext.getPerformContext().getBizType().toBizType());
            itemPO.setUserId(performItemContext.getPerformContext().getUserId());
            itemPO.setTradeId(performItemContext.getPerformContext().getTradeId());
            itemPO.setSkuId(performItemContext.getSkuPerformContext().getSkuId());
            items.add(itemPO);
        }


        return null;
    }

    @Override
    public MemberPerformItem toMemberPerformItemWhenPerformSuccess(SkuPerformContext context, PerformItemDO item) {
        return null;
    }
}