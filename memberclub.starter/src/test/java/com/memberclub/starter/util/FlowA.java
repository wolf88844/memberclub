/**
 * @(#)FlowA.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.util;

import com.memberclub.common.log.CommonLog;
import com.memberclub.sdk.perform.util.AbstractFlowNode;
import com.yomahub.liteflow.annotation.LiteflowComponent;

/**
 * author: 掘金五阳
 */
@LiteflowComponent("FlowAaaa")
public class FlowA extends AbstractFlowNode<FlowContext> {

    @Override
    public void process() throws Exception {
        FlowContext context = this.getContextBean(FlowContext.class);
        CommonLog.warn("执行到 FlowA");

    }


    @Override
    public void onSuccess() throws Exception {
        super.onSuccess();
        CommonLog.warn("FlowA onSuccess");
    }

    @Override
    public void onError(Exception e) throws Exception {
        CommonLog.warn("FlowA onError");
    }

    @Override
    public void rollback() throws Exception {
        CommonLog.warn("FlowA rollback");
    }

    @Override
    public void afterProcess() {
        CommonLog.warn("FlowA afterProcess");
    }
}