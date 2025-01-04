/**
 * @(#)DefaultBuildPerformContextExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
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
import com.memberclub.sdk.perform.flow.build.CalculateImmediatePerformItemPeriodFlow;
import com.memberclub.sdk.perform.flow.build.CalculateOrderPeriodFlow;
import com.memberclub.sdk.perform.flow.build.InitialSkuPerformContextsFlow;
import com.memberclub.sdk.perform.flow.build.MutilBuyCountClonePerformItemFlow;
import com.memberclub.sdk.perform.flow.build.PerformContextExtraInfoBuildFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 履约上下文构建", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_MONTH_CARD})
})
public class DemoMemberBuildPerformContextExtension implements BuildPerformContextExtension {

    FlowChain<PerformContext> buildPerformContextChain = null;


    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void run() throws Exception {
        buildPerformContextChain = FlowChain.newChain(flowChainService, PerformContext.class)
                .addNode(InitialSkuPerformContextsFlow.class)
                .addNode(MutilBuyCountClonePerformItemFlow.class)
                //如果年卡周期是自然月,则可以在此处根据当前期数计算每期的天数
                .addNode(CalculateImmediatePerformItemPeriodFlow.class)//计算立即履约项 时间周期
                .addNode(CalculateOrderPeriodFlow.class)//计算订单整体有效期
                .addNode(PerformContextExtraInfoBuildFlow.class)// 构建扩展属性
        ;
    }

    @Override
    public void build(PerformContext context) {
        flowChainService.execute(buildPerformContextChain, context);
    }
}