/**
 * @(#)DefaultPerformExecuteExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute.impl;

import com.google.common.collect.ImmutableList;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.sdk.extension.perform.execute.PerformExecuteExtension;
import com.memberclub.sdk.extension.perform.post.MemberPerformMessageFlow;
import com.memberclub.sdk.flow.perform.execute.DelayPerformFlow;
import com.memberclub.sdk.flow.perform.execute.ImmediatePerformFlow;
import com.memberclub.sdk.flow.perform.execute.MemberPerformItemFlow;
import com.memberclub.sdk.flow.perform.execute.MemberResourcesLockFlow;
import com.memberclub.sdk.flow.perform.execute.PerformItemGrantFlow;
import com.memberclub.sdk.flow.perform.execute.SingleMemberPerformHisFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认执行履约扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.SCENE_MONTH_CARD, SceneEnum.SCENE_MUTIL_BUY_COUNT_CARD})//Demo会员月卡,多份数
})
public class DefaultPerformExecuteExtension implements PerformExecuteExtension {


    private FlowChain<PerformContext> flowChain;

    @Autowired
    private FlowChainService flowChainService;

    @PostConstruct
    public void init() {
        flowChain = FlowChain.newChain(flowChainService, PerformContext.class)
                .addNode(MemberResourcesLockFlow.class)
                .addNode(MemberPerformMessageFlow.class)
                .addNode(SingleMemberPerformHisFlow.class)
                .addNodeWithSubNodes(ImmediatePerformFlow.class, PerformItemContext.class,
                        ImmutableList.of(MemberPerformItemFlow.class, PerformItemGrantFlow.class))
                .addNode(DelayPerformFlow.class)

        ;
    }

    @Override
    public void execute(PerformContext context) {
        flowChainService.execute(flowChain, context);
    }
}