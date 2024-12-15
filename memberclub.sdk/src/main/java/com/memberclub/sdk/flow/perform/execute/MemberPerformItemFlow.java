/**
 * @(#)MemberPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformItemFlow extends FlowNode<PerformItemContext> {

    @Override
    public void process(PerformItemContext context) {

    }

    @Override
    public void success(PerformItemContext context) {
        super.success(context);
    }
}