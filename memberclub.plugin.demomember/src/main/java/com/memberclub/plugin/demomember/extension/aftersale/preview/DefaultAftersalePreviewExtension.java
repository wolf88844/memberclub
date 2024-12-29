/**
 * @(#)DefaultAftersalePreviewExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.aftersale.preview;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.extension.aftersale.preview.AftersalePreviewExtension;
import com.memberclub.sdk.flow.aftersale.AftersaleGetAndCheckPeriodFlow;
import com.memberclub.sdk.flow.aftersale.AftersalePreviewDegradeFlow;
import com.memberclub.sdk.flow.aftersale.AftersaleStatusCheckFlow;
import com.memberclub.sdk.flow.aftersale.CalculateRefundWayFlow;
import com.memberclub.sdk.flow.aftersale.GenerateAftersalePlanDigestFlow;
import com.memberclub.sdk.flow.aftersale.GetAndCheckAftersaleTimesFlow;
import com.memberclub.sdk.flow.aftersale.OverallCheckUsageFlow;
import com.memberclub.sdk.flow.aftersale.RealtimeCalculateUsageAmountFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认售后预览扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultAftersalePreviewExtension implements AftersalePreviewExtension {


    private FlowChain<AftersalePreviewContext> previewChain = null;

    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void init() {
        previewChain = FlowChain.newChain(flowChainService, AftersalePreviewContext.class)
                .addNode(AftersalePreviewDegradeFlow.class)
                .addNode(AftersaleStatusCheckFlow.class)
                .addNode(AftersaleGetAndCheckPeriodFlow.class)
                .addNode(GetAndCheckAftersaleTimesFlow.class)
                //.addNode(AftersaleGetUsageFlow.class)               //售后获取使用情况
                //.addNode(OfflineStatatisticsUsageAmountFlow.class) //离线统计使用金额
                .addNode(RealtimeCalculateUsageAmountFlow.class)            //实时计算使用类型
                .addNode(OverallCheckUsageFlow.class)               //完全检查使用类型
                .addNode(CalculateRefundWayFlow.class)                  //计算赔付类型
                .addNode(GenerateAftersalePlanDigestFlow.class)         //生成售后计划摘要
        ;
    }

    @Override
    public void preview(AftersalePreviewContext context) {
        flowChainService.execute(previewChain, context);
    }
}