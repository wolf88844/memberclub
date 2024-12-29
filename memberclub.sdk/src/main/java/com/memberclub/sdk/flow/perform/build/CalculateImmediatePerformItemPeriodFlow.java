/**
 * @(#)CalculatePerformItemPeriodFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.TimeRange;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.sdk.extension.perform.build.PerformItemCalculateExtension;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CalculateImmediatePerformItemPeriodFlow extends FlowNode<PerformContext> {


    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            if (CollectionUtils.isEmpty(skuPerformContext.getImmediatePerformItems())) {
                //设置立即结束时间
                context.setImmediatePerformEtime(context.getBaseTime());
                continue;
            }

            for (MemberPerformItemDO immediatePerformItem : skuPerformContext.getImmediatePerformItems()) {
                PerformItemCalculateExtension extension =
                        extensionManager.getExtension(context.toDefaultScene(), PerformItemCalculateExtension.class);
                TimeRange timeRange = extension.buildPeriod(context.getBaseTime(), immediatePerformItem);
                immediatePerformItem.setStime(timeRange.getStime());
                immediatePerformItem.setEtime(timeRange.getEtime());

                context.setImmediatePerformEtime(timeRange.getEtime());
            }
        }

    }
}