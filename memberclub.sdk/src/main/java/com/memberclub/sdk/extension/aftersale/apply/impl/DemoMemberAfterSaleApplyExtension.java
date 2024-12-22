/**
 * @(#)DemoMemberAfterSaleApplyExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.apply.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.sdk.extension.aftersale.apply.AfterSaleApplyExtension;
import com.memberclub.sdk.flow.aftersale.AfterSalePlanDigestCheckFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "示例会员售后受理扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_AFTERSALE_MONTH_CARD})
})
public class DemoMemberAfterSaleApplyExtension implements AfterSaleApplyExtension {

    FlowChain<AfterSaleApplyContext> flowChain = null;

    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void init() {
        flowChain = FlowChain.newChain(flowChainService, AfterSaleApplyContext.class)
                .addNode(AfterSalePlanDigestCheckFlow.class)
        ;
    }

    @Override
    public void apply(AfterSaleApplyContext context) {
        flowChainService.execute(flowChain, context);
    }
}