/**
 * @(#)OnceTaskTriggerScanFlow.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.infrastructure.mybatis.mappers.trade.OnceTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskTriggerScanFlow extends FlowNode<OnceTaskTriggerContext> {

    @Autowired
    private OnceTaskDao onceTaskDao;

    @Override
    public void process(OnceTaskTriggerContext context) {

    }
}