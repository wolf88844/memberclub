/**
 * @(#)FlowB.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.memberclub.sdk.flow.util.AbstractFlowNode;
import com.memberclub.common.log.CommonLog;
import com.yomahub.liteflow.annotation.LiteflowComponent;

/**
 * author: 掘金五阳
 */
@LiteflowComponent("FlowBfff")
public class FlowB extends AbstractFlowNode<FlowContext> {

    @Override
    public void process() throws Exception {
        FlowContext context = this.getContextBean(FlowContext.class);
        CommonLog.info("执行到 flowB");
    }
}