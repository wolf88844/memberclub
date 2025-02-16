/**
 * @(#)SubFlowNode.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.flow;

import lombok.Getter;
import lombok.Setter;

/**
 * author: 掘金五阳
 */
public abstract class SubFlowNode<T, S> extends FlowNode<T> {

    @Setter
    @Getter
    private FlowChain<S> subChain;

}