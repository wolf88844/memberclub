/**
 * @(#)FlowService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.flow;

import com.memberclub.common.log.CommonLog;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class FlowChainService {

    @Getter
    @Autowired
    public ApplicationContext applicationContext;

    @SneakyThrows
    public <T> void execute(FlowChain<T> chain, T context) {
        Exception exception = null;
        int index = -1;
        for (FlowNode<T> node : chain.getNodes()) {
            try {
                node.process(context);
                /*if (node instanceof SubFlowNode) {
                    SubFlowNode<T, Object> subFlowNode = ((SubFlowNode<T, Object>) node);
                    execute(subFlowNode.getSubChain(), subFlowNode.toSubContext(context));
                }*/

                index++;
            } catch (Exception e) {
                if (e instanceof SkipException) {
                    CommonLog.warn("当前流程:{} 发出 Skip请求,后续流程不再执行", node.getClass().getSimpleName());
                    break;
                }
                exception = e;
                break;
            }
        }

        if (exception != null) {

            for (int i = index; i >= 0; i--) {
                FlowNode<T> node = chain.getNodes().get(i);
                try {
                    node.rollback(context, exception);
                } catch (Exception e) {
                    CommonLog.error("rollback执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
                }
            }
        } else {
            for (int i = index; i >= 0; i--) {
                FlowNode<T> node = chain.getNodes().get(i);
                try {
                    node.success(context);
                } catch (Exception e) {
                    CommonLog.error("success 执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
                }
            }
        }

        for (int i = index; i >= 0; i--) {
            FlowNode<T> node = chain.getNodes().get(i);
            try {
                node.callback(context, exception);
            } catch (Exception e) {
                CommonLog.error("callback执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
            }
        }
        if (exception != null) {
            throw exception;
        }
    }
}