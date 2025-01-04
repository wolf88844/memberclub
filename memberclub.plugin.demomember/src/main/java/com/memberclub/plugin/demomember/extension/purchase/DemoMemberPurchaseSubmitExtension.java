/**
 * @(#)DemoMemberPurchaseSubmitExtension.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.plugin.demomember.extension.purchase;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.sdk.purchase.extension.PurchaseSubmitExtension;
import com.memberclub.sdk.purchase.flow.MemberOrderSubmitFlow;
import com.memberclub.sdk.purchase.flow.PurchaseSubmitLockFlow;
import com.memberclub.sdk.purchase.flow.SkuInfoInitalSubmitFlow;
import com.memberclub.sdk.purchase.flow.TradeOrderSubmitFlow;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 购买提单扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.HOMEPAGE_SUBMIT_SCENE})
})
public class DemoMemberPurchaseSubmitExtension implements PurchaseSubmitExtension {

    private static FlowChain<PurchaseSubmitContext> flowChain = null;

    @PostConstruct
    public void init() {
        flowChain = FlowChain.newChain(PurchaseSubmitContext.class)
                .addNode(PurchaseSubmitLockFlow.class)
                .addNode(SkuInfoInitalSubmitFlow.class)
                .addNode(MemberOrderSubmitFlow.class)
                .addNode(TradeOrderSubmitFlow.class)
        ;
    }

    @Override
    public void submit(PurchaseSubmitContext context) {
        flowChain.execute(context);
    }
}