/**
 * @(#)TestFlow.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.google.common.collect.ImmutableList;
import com.memberclub.common.flow.FlowChain;
import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.sdk.flow.util.LiteFlowChain;
import com.memberclub.sdk.flow.util.LiteFlowChainService;
import com.memberclub.starter.AppStarter;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author: 掘金五阳
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppStarter.class})
public class TestFlow {

    @Autowired
    private FlowExecutor flowExecutor;

    @Autowired
    private LiteFlowChainService liteFlowChainService;

    private LiteFlowChain<FlowContext> liteFlowChain = null;

    private FlowChain<FlowContext> flowChainAndSub = null;

    @Before
    public void init() {
        //LiteFlowChainELBuilder.createChain().setChainId("test_chain").setEL("THEN(FlowA, FlowB)").preBuild();
        liteFlowChain = liteFlowChainService.newChain(FlowContext.class).addNode(FlowA.class)
                .addNode(FlowB.class)
                .build("test_chain");

        flowChain = FlowChain.newChain(flowChainService, FlowContext.class)
                .addNode(FlowC.class)
                .addNode(FlowD.class);

        flowChainAndSub = FlowChain.newChain(flowChainService, FlowContext.class)
                .addNodeWithSubNodes(FlowC.class, SubFlowContext.class, ImmutableList.of(FlowC1.class, FlowC2.class))
                .addNode(FlowD.class);
    }

    @Test
    public void test() {
        FlowContext context = new FlowContext();
        //flowExecutor.execute2Resp("test_chain", context, FlowContext.class);
        LiteflowResponse response = liteFlowChain.execute(flowExecutor, context);
        System.out.println(JsonUtils.toJson(response.isSuccess()));
    }

    @Autowired
    private FlowChainService flowChainService;


    private FlowChain<FlowContext> flowChain = null;


    @Test()
    public void test2() {
        FlowContext context = new FlowContext();
        flowChainService.execute(flowChain, context);
    }

    @Test()
    public void test3() {
        FlowContext context = new FlowContext();
        flowChainService.execute(flowChainAndSub, context);
    }
}