/**
 * @(#)InitialSkuPerformContextsFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.extension.perform.PerformItemCalculateExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class InitialSkuPerformContextsFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManger extensionManger;

    @Override
    public void process(PerformContext context) {
        List<SkuBuyDetailDO> details = context.getSkuBuyDetails();

        List<SkuPerformContext> skuPerformContexts = Lists.newArrayList();
        for (SkuBuyDetailDO detail : details) {
            SkuPerformContext skuPerformContext = PerformConvertor.INSTANCE.toSkuPerformContext(detail);
            PerformItemCalculateExtension calculateExtension =
                    extensionManger.getExtension(context.toDefaultScene(), PerformItemCalculateExtension.class);

            List<PerformItemDO> items = Lists.newArrayList();
            for (SkuPerformItemConfigDO config : detail.getSkuSnapshot().getPerformConfig().getConfigs()) {
                PerformItemDO item = calculateExtension.toPerformItem(config);
                items.add(item);
            }
            skuPerformContext.setImmediatePerformItems(items);
            skuPerformContexts.add(skuPerformContext);
        }
        context.setSkuPerformContexts(skuPerformContexts);
        if (context.getBaseTime() == 0) {
            context.setBaseTime(TimeUtil.now());
        }
    }
}