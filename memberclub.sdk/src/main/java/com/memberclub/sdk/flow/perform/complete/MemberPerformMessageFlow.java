/**
 * @(#)MemberPerformMessageFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.complete;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformMessageFlow extends FlowNode<PerformContext> {

    @Override
    public void process(PerformContext context) {

    }

    @Override
    public void success(PerformContext context) {
        // TODO: 2024/12/15 发送消息
    }
}