/**
 * @(#)FlowChain.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.util;

import com.memberclub.common.log.CommonLog;
import com.yomahub.liteflow.annotation.LiteflowComponent;
import com.yomahub.liteflow.builder.el.LiteFlowChainELBuilder;
import com.yomahub.liteflow.core.FlowExecutor;
import com.yomahub.liteflow.flow.LiteflowResponse;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * author: 掘金五阳
 */
@Deprecated
@Data
public class LiteFlowChain<T> {

    @Autowired
    private ApplicationContext applicationContext;

    private String chainId;

    private List<Class<? extends AbstractFlowNode<T>>> nodes = new ArrayList<>();

    public LiteFlowChain(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public LiteFlowChain<T> addNodes(Class<? extends AbstractFlowNode<T>>... components) {
        for (Class<? extends AbstractFlowNode<T>> component : components) {
            LiteflowComponent liteflowComponent = AnnotationUtils.findAnnotation(component, LiteflowComponent.class);
            Object bean = applicationContext.getBean(liteflowComponent.value());
            nodes.add(component);
        }
        return this;
    }

    public LiteFlowChain<T> addNode(Class<? extends AbstractFlowNode<T>> component) {
        LiteflowComponent liteflowComponent = AnnotationUtils.findAnnotation(component, LiteflowComponent.class);
        Object bean = applicationContext.getBean(liteflowComponent.value());

        nodes.add(component);
        return this;
    }

   /* @SuppressWarnings("unchecked")
    public static <T> FlowChain<T> newChain(Class<? extends AbstractFlowNode<T>>... components) {
        FlowChain<T> flowChain = new FlowChain<T>();
        flowChain.addNodes(components);
        return flowChain;
    }*/

    public LiteFlowChain<T> build(String chainId) {
        this.chainId = chainId;
        String el = buildEl(nodes);
        LiteFlowChainELBuilder.createChain().setChainId(chainId)
                .setEL(el).build();
        CommonLog.info("构建流程成功 chainId:{}, el:{}", chainId, el);
        return this;
    }


    @SuppressWarnings("unchecked")
    public <T> LiteflowResponse execute(FlowExecutor flowExecutor, T t) {
        return flowExecutor.execute2Resp(chainId, t, t.getClass());
    }


    public static <T> String buildEl(List<Class<? extends AbstractFlowNode<T>>> classes) {
        String[] classSimpleName = new String[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            LiteflowComponent component = classes.get(i).getAnnotation(LiteflowComponent.class);
            String value = component.value();

            classSimpleName[i] = value;
        }
        String el = String.format("%s(%s)", "THEN", StringUtils.join(classSimpleName, ","));
        return el;
    }
}