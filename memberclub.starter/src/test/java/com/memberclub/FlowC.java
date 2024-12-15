/**
 * @(#)FlowC.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.memberclub.common.flow.FlowChainService;
import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.common.log.CommonLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class FlowC extends SubFlowNode<FlowContext, SubFlowContext> {

    @Autowired
    private FlowChainService flowChainService;

    @Override
    public void process(FlowContext flowContext) {
        CommonLog.info("FlowC process");
        flowChainService.execute(getSubChain(), new SubFlowContext());
    }

    @Override
    public void success(FlowContext flowContext) {
        CommonLog.info("FlowC success");
    }

    @Override
    public void rollback(FlowContext flowContext) {
        CommonLog.info("FlowC rollback");
    }

    @Override
    public void callback(FlowContext flowContext, Exception e) {
        if (e != null) {
            CommonLog.error("callback 是异常", e);
        }
        CommonLog.info("FlowC callback");
    }


}