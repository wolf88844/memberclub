/**
 * @(#)DefaultBuildPerformContextExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.build.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.sdk.extension.perform.build.BuildPerformContextExtension;
import com.memberclub.sdk.flow.perform.build.CalculateDelayPerformItemPeriodFlow;
import com.memberclub.sdk.flow.perform.build.CalculateImmediatePerformItemPeriodFlow;
import com.memberclub.sdk.flow.perform.build.CalculateOrderPeriodFlow;
import com.memberclub.sdk.flow.perform.build.InitialSkuPerformContextsFlow;
import com.memberclub.sdk.flow.perform.build.MutilBuyCountClonePerformItemFlow;
import com.memberclub.sdk.flow.perform.build.MutilPeriodMemberClonePerformItemFlow;
import com.memberclub.sdk.flow.perform.build.PerformContextCommonPropertyBuildFlow;
import com.memberclub.sdk.flow.perform.build.PerformContextExtraPropertyBuildFlow;
import com.memberclub.sdk.flow.perform.build.QuerySkuBuyDetailsFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认履约上下文构建", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_MONTH_CARD, SceneEnum.SCENE_MUTIL_PERIOD_CARD})
})
public class DefaultBuildPerformContextExtension implements BuildPerformContextExtension {

    FlowChain<PerformContext> buildPerformContextChain = null;


    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void run() throws Exception {
        buildPerformContextChain = FlowChain.newChain(flowChainService, PerformContext.class)
                .addNode(QuerySkuBuyDetailsFlow.class)
                .addNode(InitialSkuPerformContextsFlow.class)
                .addNode(MutilBuyCountClonePerformItemFlow.class)
                .addNode(MutilPeriodMemberClonePerformItemFlow.class)
                .addNode(CalculateImmediatePerformItemPeriodFlow.class)//计算立即履约项 时间周期
                .addNode(CalculateDelayPerformItemPeriodFlow.class)//计算延迟履约项 时间周期
                .addNode(CalculateOrderPeriodFlow.class)//计算订单整体有效期
                .addNode(PerformContextCommonPropertyBuildFlow.class)// 构建通用属性
                .addNode(PerformContextExtraPropertyBuildFlow.class)//构建其他扩展属性
        ;
    }

    @Override
    public void build(PerformContext context) {
        flowChainService.execute(buildPerformContextChain, context);
    }
}