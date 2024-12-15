/**
 * @(#)TestFlow.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.memberclub.sdk.flow.util.FlowChain;
import com.memberclub.starter.AppStarter;
import com.memberclub.common.util.JsonUtils;
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

    private FlowChain<FlowContext> flowChain = null;

    @Before
    public void init() {
        //LiteFlowChainELBuilder.createChain().setChainId("test_chain").setEL("THEN(FlowA, FlowB)").build();
        flowChain = FlowChain.newChain(FlowA.class)
                .addNode(FlowB.class)
                .build("test_chain");
    }

    @Test
    public void test() {
        FlowContext context = new FlowContext();
        //flowExecutor.execute2Resp("test_chain", context, FlowContext.class);
        LiteflowResponse response = flowChain.execute(flowExecutor, context);
        System.out.println(JsonUtils.toJson(response.isSuccess()));
    }
}