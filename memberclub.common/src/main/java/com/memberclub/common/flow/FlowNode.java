/**
 * @(#)FlowNode.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.flow;

/**
 * author: 掘金五阳
 */
public abstract class FlowNode<T> {
    

    public abstract void process(T t);

    public void success(T t) {
    }

    public void rollback(T t) {
    }

    public void callback(T t, Exception e) {
    }
}