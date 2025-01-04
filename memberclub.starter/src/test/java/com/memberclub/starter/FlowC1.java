/**
 * @(#)FlowC1.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class FlowC1 extends FlowNode<SubFlowContext> {

    @Override
    public void process(SubFlowContext flowContext) {
        CommonLog.info("FlowC1 process");
    }

    @Override
    public void success(SubFlowContext flowContext) {
        CommonLog.info("FlowC1 success");
    }

    @Override
    public void rollback(SubFlowContext flowContext, Exception e) {
        CommonLog.info("FlowC1 rollback");
    }

    @Override
    public void callback(SubFlowContext flowContext, Exception e) {
        if (e != null) {
            CommonLog.error("callback 是异常", e);
        }
        CommonLog.info("FlowC1 callback");
    }
}