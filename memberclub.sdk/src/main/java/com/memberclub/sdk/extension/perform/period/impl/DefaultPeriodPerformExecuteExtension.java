/**
 * @(#)DefaultPeriodPerformExecuteExtension.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.period.impl;

import com.google.common.collect.ImmutableList;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import com.memberclub.sdk.extension.perform.period.PeriodPerformExecuteExtension;
import com.memberclub.sdk.flow.perform.execute.MemberPerformItemFlow;
import com.memberclub.sdk.flow.perform.execute.PerformItemGrantFlow;
import com.memberclub.sdk.flow.perform.period.PeriodPerformImmediatePerformFlow;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "周期履约执行扩展点实现", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER)
})
public class DefaultPeriodPerformExecuteExtension implements PeriodPerformExecuteExtension {

    @Autowired
    private FlowChainService chainService;

    private FlowChain<PeriodPerformContext> flowChain = null;

    @PostConstruct
    public void init() {
        flowChain = FlowChain.newChain(chainService, PeriodPerformContext.class)
                .addNodeWithSubNodes(PeriodPerformImmediatePerformFlow.class, PerformItemContext.class,
                        ImmutableList.of(MemberPerformItemFlow.class, PerformItemGrantFlow.class));

    }

    @Override
    public void periodPerform(PeriodPerformContext context) {
        chainService.execute(flowChain, context);
    }


    @Override
    public void buildContext(OnceTaskDO task, PeriodPerformContext context) {
        context.setContent(((PerformTaskContentDO) task.getContent()));
        context.setSkuId(context.getContent().getSkuId());
        context.setTradeId(context.getContent().getTradeId());
    }
}