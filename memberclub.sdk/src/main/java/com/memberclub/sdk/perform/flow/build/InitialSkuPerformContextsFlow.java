/**
 * @(#)InitialSkuPerformContextsFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.MemberPerformHisStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformHisDO;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.infrastructure.id.IdGenerator;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.perform.extension.build.PerformItemCalculateExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class InitialSkuPerformContextsFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(PerformContext context) {
        List<SkuBuyDetailDO> details = context.getSkuBuyDetails();

        List<SkuPerformContext> skuPerformContexts = Lists.newArrayList();
        for (SkuBuyDetailDO detail : details) {
            SkuPerformContext skuPerformContext = new SkuPerformContext();
            skuPerformContext.setSkuBuyDetail(detail);
            MemberPerformHisDO his = PerformConvertor.INSTANCE.toMemberPerformHisDO(context);
            skuPerformContext.setHis(his);

            his.setStatus(MemberPerformHisStatusEnum.INIT);
            his.setSkuId(detail.getSkuId());
            his.setBuyCount(detail.getBuyCount());
            his.setCtime(TimeUtil.now());
            his.setUtime(TimeUtil.now());
            String hisToken = buildPerformHisToken(context, detail);
            his.setPerformHisToken(hisToken);

            PerformItemCalculateExtension calculateExtension =
                    extensionManager.getExtension(context.toDefaultScene(), PerformItemCalculateExtension.class);

            List<MemberPerformItemDO> items = Lists.newArrayList();
            for (SkuPerformItemConfigDO config : detail.getSkuSnapshot().getPerformConfig().getConfigs()) {
                MemberPerformItemDO item = calculateExtension.toPerformItem(config);
                item.setSkuId(detail.getSkuId());
                items.add(item);
            }
            skuPerformContext.setImmediatePerformItems(items);
            skuPerformContexts.add(skuPerformContext);
        }
        context.setSkuPerformContexts(skuPerformContexts);
        if (context.getBaseTime() == 0) {
            context.setBaseTime(TimeUtil.now());
            context.getCmd().setBaseTime(context.getBaseTime());
        }
    }

    private String buildPerformHisToken(PerformContext context, SkuBuyDetailDO detail) {
        MemberPerformHis hisFromDb = context.matchHisFromDb(detail.getSkuId());
        if (hisFromDb != null) {
            return hisFromDb.getPerformHisToken();
        } else {
            IdGenerator idGenerator = extensionManager.getExtension(context.toDefaultScene(),
                    IdGenerator.class);
            String performHisToken = idGenerator.generateId();
            return performHisToken;
        }
    }
}