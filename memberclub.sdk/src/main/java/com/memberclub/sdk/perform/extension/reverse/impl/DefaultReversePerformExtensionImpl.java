/**
 * @(#)DefaultReversePerformExtensionImpl.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.reverse.impl;

import com.google.common.collect.Lists;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.sdk.perform.extension.reverse.ReversePerformExtension;
import com.memberclub.sdk.perform.flow.reverse.BuildReversePerformInfosFlow;
import com.memberclub.sdk.perform.flow.reverse.MutilReversePerformItemFlow;
import com.memberclub.sdk.perform.flow.reverse.ReverseAssetsFlow;
import com.memberclub.sdk.perform.flow.reverse.ReversePerformSubOrderFlow;
import com.memberclub.sdk.perform.flow.reverse.ReversePerformItemFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "逆向履约流程扩展", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT)
})
public class DefaultReversePerformExtensionImpl implements ReversePerformExtension {

    private FlowChain<ReversePerformContext> reversePerformChain;

    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void init() {
        reversePerformChain = FlowChain.newChain(flowChainService, ReversePerformContext.class)
                .addNode(BuildReversePerformInfosFlow.class)//构建逆向履约信息
                .addNode(ReversePerformSubOrderFlow.class)//逆向履约单
                .addNodeWithSubNodes(MutilReversePerformItemFlow.class, ReversePerformContext.class,
                        Lists.newArrayList(ReversePerformItemFlow.class,//逆向履约项
                                ReverseAssetsFlow.class))//// 逆向资产
        //addNote 清理 Tasks
        ;
    }

    @Override
    public void reverse(ReversePerformContext context) {
        flowChainService.execute(reversePerformChain, context);
    }
}