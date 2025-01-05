/**
 * @(#)RealtimeCalculateUsageFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.preview;

import com.google.common.collect.Maps;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeCalculateTypeEnum;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.context.aftersale.preview.ItemUsage;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.sdk.aftersale.extension.preview.AftersaleAmountExtension;
import com.memberclub.sdk.aftersale.extension.preview.RealtimeCalculateUsageExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 * 实时计算使用类型
 */
@Service
public class RealtimeCalculateUsageAmountFlow extends FlowNode<AftersalePreviewContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(AftersalePreviewContext context) {
        if (context.getCurrentSubOrderDO() == null) {
            context.setCurrentSubOrderDO(context.getSubOrders().get(0));
        }

        context.setUsageTypeCalculateType(UsageTypeCalculateTypeEnum.USE_AMOUNT);
        Map<RightTypeEnum, List<MemberPerformItemDO>> rightType2Items = context.getPerformItems()
                .stream().filter(p -> p.getSkuId() == context.getCurrentSubOrderDO().getSkuId())
                .collect(Collectors.groupingBy(MemberPerformItemDO::getRightType));

        Map<String, ItemUsage> batchCode2ItemUsage = Maps.newHashMap();

        for (Map.Entry<RightTypeEnum, List<MemberPerformItemDO>> entry : rightType2Items.entrySet()) {
            context.setCurrentPerformItemsGroupByRightType(entry.getValue());
            context.setCurrentRightType(entry.getKey().getCode());

            Map<String, ItemUsage> tempBatchCode2ItemUsage =
                    extensionManager.getExtension(BizScene.of(context.getCmd().getBizType().getCode(),
                            SceneEnum.buildRightTypeScene(entry.getKey().getCode())),
                            RealtimeCalculateUsageExtension.class).calculateItemUsage(context);
            batchCode2ItemUsage.putAll(tempBatchCode2ItemUsage);
        }

        context.setCurrentBatchCode2ItemUsage(batchCode2ItemUsage);

        int recommendRefundPrice =
                extensionManager.getExtension(BizScene.of(context.getCmd().getBizType().getCode()),
                        AftersaleAmountExtension.class).calculteRecommendRefundPrice(context, batchCode2ItemUsage);
        context.setRecommendRefundPrice(recommendRefundPrice);
    }
}