/**
 * @(#)DefaultBuildReverseInfoExtension.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.reverse.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.sdk.oncetask.periodperform.service.PeriodPerformTaskDomainService;
import com.memberclub.sdk.perform.extension.reverse.BuildReverseInfoExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认构建逆向履约信息", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT)
})
public class DefaultBuildReversePerformInfoExtension implements BuildReverseInfoExtension {

    @Autowired
    private PerformDomainService performDomainService;

    @Autowired
    private PeriodPerformTaskDomainService periodPerformTaskDomainService;

    @Override
    public void buildAssets(ReversePerformContext context) {
        performDomainService.buildReversePerformAssetsInfo(context, context.getSubTradeId2SubOrderReversePerformContext());
    }

    @Override
    public void buildTasks(ReversePerformContext context) {
        periodPerformTaskDomainService.buildActivePeriodTasks(context, context.getSubTradeId2SubOrderReversePerformContext());
    }
}