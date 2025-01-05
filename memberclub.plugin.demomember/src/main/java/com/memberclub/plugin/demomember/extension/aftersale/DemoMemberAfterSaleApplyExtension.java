/**
 * @(#)DemoMemberAfterSaleApplyExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.aftersale;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.sdk.aftersale.extension.apply.AfterSaleApplyExtension;
import com.memberclub.sdk.aftersale.flow.apply.AfterSalePlanDigestCheckFlow;
import com.memberclub.sdk.aftersale.flow.apply.AftersaleApplyLockFlow;
import com.memberclub.sdk.aftersale.flow.apply.AftersaleApplyPreviewFlow;
import com.memberclub.sdk.aftersale.flow.apply.AftersaleDoApplyFlow;
import com.memberclub.sdk.aftersale.flow.apply.AftersaleGenerateOrderFlow;
import com.memberclub.sdk.aftersale.flow.doapply.AftersaleAsyncRollbackFlow;
import com.memberclub.sdk.aftersale.flow.doapply.AftersaleOrderFlow;
import com.memberclub.sdk.aftersale.flow.doapply.AftersaleRefundOrderFlow;
import com.memberclub.sdk.aftersale.flow.doapply.AftersaleReversePurchaseFlow;
import com.memberclub.sdk.aftersale.flow.doapply.AftersaleReversePerformFlow;
import com.memberclub.sdk.aftersale.flow.doapply.MemberOrderRefundFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "示例会员售后受理扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_AFTERSALE_MONTH_CARD})
})
public class DemoMemberAfterSaleApplyExtension implements AfterSaleApplyExtension {


    FlowChain<AfterSaleApplyContext> applyFlowChain = null;

    FlowChain<AfterSaleApplyContext> checkFlowChain = null;

    FlowChain<AfterSaleApplyContext> doApplyFlowChain = null;

    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void init() {
        applyFlowChain = FlowChain.newChain(flowChainService, AfterSaleApplyContext.class)
                .addNode(AftersaleApplyLockFlow.class)     //加锁
                .addNode(AftersaleApplyPreviewFlow.class)       //售后预览
                .addNode(AfterSalePlanDigestCheckFlow.class)    //校验售后计划摘要
                .addNode(AftersaleGenerateOrderFlow.class)      //生成售后单
                .addNode(AftersaleDoApplyFlow.class)
        ;

        doApplyFlowChain = FlowChain.newChain(flowChainService, AfterSaleApplyContext.class)
                .addNode(AftersaleOrderFlow.class)
                .addNode(MemberOrderRefundFlow.class)
                .addNode(AftersaleAsyncRollbackFlow.class)
                .addNode(AftersaleReversePerformFlow.class)
                .addNode(AftersaleReversePurchaseFlow.class)
                .addNode(AftersaleRefundOrderFlow.class)
        //.addNode()
        ;


    }

    @Override
    public void apply(AfterSaleApplyContext context) {
        flowChainService.execute(applyFlowChain, context);
    }

    @Override
    public void doApply(AfterSaleApplyContext context) {
        flowChainService.execute(doApplyFlowChain, context);
    }

    @Override
    public void customBuildAftersaleOrder(AfterSaleApplyContext context, AftersaleOrderDO aftersaleOrderDO) {

    }
}