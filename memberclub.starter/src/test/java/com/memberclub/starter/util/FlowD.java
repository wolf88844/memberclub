/**
 * @(#)FlowD.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.util;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class FlowD extends FlowNode<FlowContext> {

    @Override
    public void process(FlowContext flowContext) {
        CommonLog.info("FlowD process");
        //ResultCode.CAN_NOT_PERFORM_RETRY.newException();
    }

    @Override
    public void success(FlowContext flowContext) {
        CommonLog.info("FlowD success");
    }

    @Override
    public void rollback(FlowContext flowContext, Exception e) {
        CommonLog.info("FlowD rollback");
    }

    @Override
    public void callback(FlowContext flowContext, Exception e) {
        CommonLog.info("FlowD callback");
    }
}