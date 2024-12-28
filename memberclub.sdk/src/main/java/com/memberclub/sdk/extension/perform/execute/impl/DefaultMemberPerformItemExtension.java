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
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.extension.perform.build.PerformItemCalculateExtension;
import com.memberclub.sdk.extension.perform.execute.MemberPerformItemExtension;
import com.memberclub.sdk.uti.BizUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "构建履约项扩展点,默认实现", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultMemberPerformItemExtension implements MemberPerformItemExtension {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public List<MemberPerformItem> toMemberPerformItems(PerformItemContext performItemContext) {
        PerformItemCalculateExtension extension =
                extensionManager.getExtension(
                        performItemContext.getPerformContext().toDefaultScene(), PerformItemCalculateExtension.class);


        List<MemberPerformItem> items = Lists.newArrayList();
        for (MemberPerformItemDO item : performItemContext.getItems()) {
            MemberPerformItem itemPO = PerformConvertor.INSTANCE.toMemberPerformItem(item);
            itemPO.setBizType(performItemContext.getPerformContext().getBizType().toBizType());
            itemPO.setUserId(performItemContext.getPerformContext().getUserId());
            itemPO.setTradeId(performItemContext.getPerformContext().getTradeId());
            itemPO.setSkuId(performItemContext.getSkuPerformContext().getHis().getSkuId());
            itemPO.setCtime(TimeUtil.now());
            itemPO.setUtime(TimeUtil.now());
            itemPO.setStatus(PerformItemStatusEnum.INIT.toInt());

            String itemToken = BizUtils.toItemToken(
                    performItemContext.getSkuPerformContext().getHis().getPerformHisToken(),
                    item.getRightId(),
                    item.getBuyIndex(), item.getPhase());
            itemPO.setItemToken(itemToken);
            item.setItemToken(itemPO.getItemToken());
            items.add(itemPO);
        }


        return items;
    }

    @Override
    public MemberPerformItem toMemberPerformItemWhenPerformSuccess(SkuPerformContext context, MemberPerformItemDO item) {
        return null;
    }
}