/**
 * @(#)AftersaleUsageService.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.service.domain;

import com.google.common.collect.Maps;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.context.aftersale.preview.ItemUsage;
import com.memberclub.domain.context.perform.common.RightTypeEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.sdk.aftersale.extension.preview.RealtimeCalculateUsageExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleUsageService {

    @Autowired
    private ExtensionManager extensionManager;


    public Map<String, ItemUsage> buildUsageMap(AftersalePreviewContext context) {
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
        return batchCode2ItemUsage;
    }
}