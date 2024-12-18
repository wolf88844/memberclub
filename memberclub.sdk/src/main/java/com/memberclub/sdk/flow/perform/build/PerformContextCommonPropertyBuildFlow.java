/**
 * @(#)PerformContextCommonPropertyBuildFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.sdk.extension.perform.PerformItemCalculateExtension;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PerformContextCommonPropertyBuildFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManger extensionManger;

    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            PerformItemCalculateExtension extension =
                    extensionManger.getExtension(context.toDefaultScene(), PerformItemCalculateExtension.class);

            for (PerformItemDO immediatePerformItem : skuPerformContext.getImmediatePerformItems()) {
                immediatePerformItem.setItemToken(extension.buildItemToken(context,
                        skuPerformContext, immediatePerformItem));
            }

            if (CollectionUtils.isNotEmpty(skuPerformContext.getDelayPerformItems())) {
                for (PerformItemDO delayPerformItem : skuPerformContext.getDelayPerformItems()) {
                    delayPerformItem.setItemToken(extension.buildItemToken(context,
                            skuPerformContext, delayPerformItem));
                }
            }

        }
    }
}