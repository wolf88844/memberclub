/**
 * @(#)FlowChain.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.util;

import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class FlowChain<T> {

    private String chainId;

    private List<Class<? extends AbstractFlowNode<T>>> nodes = new ArrayList<>();

    public FlowChain<T> addNodes(Class<? extends AbstractFlowNode<T>>... components) {
        for (Class<? extends AbstractFlowNode<T>> component : components) {
            nodes.add(component);
        }
        return this;
    }

    public FlowChain<T> addNode(Class<? extends AbstractFlowNode<T>> component) {
        nodes.add(component);
        return this;
    }

    @SuppressWarnings("unchecked")
    public static <T> FlowChain<T> newChain(Class<? extends AbstractFlowNode<T>>... components) {
        FlowChain<T> flowChain = new FlowChain<T>();
        flowChain.addNodes(components);
        return flowChain;
    }

    public FlowChain<T> build(String chainId) {
        this.chainId = chainId;
        LiteFlowChainELBuilder.createChain().setChainId(chainId)
                .setEL(buildEl(nodes)).build();
        return this;
    }


    @SuppressWarnings("unchecked")
    public <T> LiteflowResponse execute(FlowExecutor flowExecutor, T t) {
        return flowExecutor.execute2Resp(chainId, t, t.getClass());
    }


    public static <T> String buildEl(List<Class<? extends AbstractFlowNode<T>>> classes) {
        String[] classSimpleName = new String[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            classSimpleName[i] = classes.get(i).getSimpleName();
        }
        String el = String.format("%s(%s)", "THEN", StringUtils.join(classSimpleName, ","));
        return el;
    }
}