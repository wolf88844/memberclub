/**
 * @(#)DemoMemberBuildPerformContextForMutilPeriodCardExtension.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.perform;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.sdk.perform.extension.build.BuildPerformContextExtension;
import com.memberclub.sdk.perform.flow.build.CalculateDelayPerformItemPeriodFlow;
import com.memberclub.sdk.perform.flow.build.CalculateImmediatePerformItemPeriodFlow;
import com.memberclub.sdk.perform.flow.build.CalculateOrderPeriodFlow;
import com.memberclub.sdk.perform.flow.build.InitialSkuPerformContextsFlow;
import com.memberclub.sdk.perform.flow.build.MutilBuyCountClonePerformItemFlow;
import com.memberclub.sdk.perform.flow.build.MutilPeriodMemberClonePerformItemFlow;
import com.memberclub.sdk.perform.flow.build.PerformContextExtraInfoBuildFlow;
import com.memberclub.sdk.perform.flow.build.QuerySkuBuyDetailsFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 年卡履约上下文构建", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_MUTIL_PERIOD_CARD})
})
public class DemoMemberBuildPerformContextForMutilPeriodCardExtension implements BuildPerformContextExtension {

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
                //如果年卡周期是自然月,则可以在此处根据当前期数计算每期的天数
                .addNode(CalculateImmediatePerformItemPeriodFlow.class)//计算立即履约项 时间周期
                .addNode(CalculateDelayPerformItemPeriodFlow.class)//计算延迟履约项 时间周期
                .addNode(CalculateOrderPeriodFlow.class)//计算订单整体有效期
                .addNode(PerformContextExtraInfoBuildFlow.class)// 构建扩展属性
        ;
    }

    @Override
    public void build(PerformContext context) {
        flowChainService.execute(buildPerformContextChain, context);
    }
}