/**
 * @(#)FlowB.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.memberclub.common.log.CommonLog;
import com.memberclub.sdk.perform.util.AbstractFlowNode;
import com.yomahub.liteflow.annotation.LiteflowComponent;

/**
 * author: 掘金五阳
 */
@LiteflowComponent("FlowBfff")
public class FlowB extends AbstractFlowNode<FlowContext> {

    @Override
    public void process() throws Exception {
        FlowContext context = this.getContextBean(FlowContext.class);
        CommonLog.warn("执行到 flowB");
        //ResultCode.CAN_NOT_PERFORM_RETRY.newException();
    }


    @Override
    public void onSuccess() throws Exception {
        super.onSuccess();
        CommonLog.warn("FlowB onSuccess");
    }

    @Override
    public void onError(Exception e) throws Exception {
        CommonLog.warn("FlowB onError");
    }

    @Override
    public void rollback() throws Exception {
        CommonLog.warn("FlowB rollback");
    }

    @Override
    public void afterProcess() {
        CommonLog.warn("FlowB afterProcess");
    }
}