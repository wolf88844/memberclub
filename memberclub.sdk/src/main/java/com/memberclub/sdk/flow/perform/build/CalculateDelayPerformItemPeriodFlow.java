/**
 * @(#)CalculateDelayPerformItemPeriodFlow.java, 十二月 15, 2024.
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
import com.memberclub.sdk.uti.BizUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CalculateDelayPerformItemPeriodFlow extends FlowNode<PerformContext> {


    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            if (CollectionUtils.isEmpty(skuPerformContext.getDelayPerformItems())) {
                continue;
            }

            long stime = context.getImmediatePerformEtime();
            for (MemberPerformItemDO delayItem : skuPerformContext.getDelayPerformItems()) {
                // TODO: 2024/12/15
                TimeRange timeRange = extensionManager.getExtension(context.toDefaultScene(),
                        PerformItemCalculateExtension.class).buildDelayPeriod(stime, delayItem);
                delayItem.setStime(timeRange.getStime());
                delayItem.setEtime(timeRange.getEtime());
                stime = timeRange.getEtime() + 1;

                String itemToken = BizUtils.toItemToken(
                        skuPerformContext.getHis().getPerformHisToken(),
                        delayItem.getRightId(),
                        delayItem.getBuyIndex(), delayItem.getPhase());
                delayItem.setItemToken(itemToken);
            }
        }
    }
}