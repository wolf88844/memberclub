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
import com.memberclub.sdk.purchase.extension.PurchaseExtension;
import com.memberclub.sdk.purchase.flow.CommonOrderSubmitFlow;
import com.memberclub.sdk.purchase.flow.MemberOrderSubmitFlow;
import com.memberclub.sdk.purchase.flow.PurchaseMarkNewMemberFlow;
import com.memberclub.sdk.purchase.flow.PurchaseOperateInventoryFlow;
import com.memberclub.sdk.purchase.flow.PurchaseSubmitLockFlow;
import com.memberclub.sdk.purchase.flow.PurchaseUserQuotaFlow;
import com.memberclub.sdk.purchase.flow.PurchaseValidateInventoryFlow;
import com.memberclub.sdk.purchase.flow.SkuInfoInitalSubmitFlow;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "DemoMember 购买提单扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.HOMEPAGE_SUBMIT_SCENE})
})
public class DemoMemberPurchaseExtension implements PurchaseExtension {

    private static FlowChain<PurchaseSubmitContext> flowChain = null;

    @PostConstruct
    public void init() {
        flowChain = FlowChain.newChain(PurchaseSubmitContext.class)
                .addNode(PurchaseSubmitLockFlow.class)
                .addNode(SkuInfoInitalSubmitFlow.class)
                .addNode(PurchaseUserQuotaFlow.class)//检查限额
                .addNode(PurchaseValidateInventoryFlow.class)//检查库存
                .addNode(MemberOrderSubmitFlow.class)// 会员提单
                .addNode(PurchaseMarkNewMemberFlow.class)//新会员标记
                .addNode(PurchaseOperateInventoryFlow.class)//扣减库存
                .addNode(CommonOrderSubmitFlow.class)//订单系统提单
        ;
    }

    @Override
    public void submit(PurchaseSubmitContext context) {
        flowChain.execute(context);
    }
}