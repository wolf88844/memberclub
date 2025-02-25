/**
 * @(#)FlowChainService.java, 十二月 15, 2024.
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

import java.util.List;

/**
 * FlowChainService 是一个用于管理流程链的服务类。
 * 它负责执行流程链中的各个节点，并处理节点的正常执行、回滚、成功回调和异常回调逻辑。
 *
 * @author 掘金五阳
 */
@Service
public class FlowChainService {

    /**
     * Spring 应用上下文，用于获取Spring容器中的Bean。
     */
    @Getter
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 执行流程链中的所有节点。
     * 按顺序执行每个节点的process方法。如果某个节点抛出SkipException，则停止后续节点的执行；
     * 如果抛出其他异常，则记录异常并尝试回滚已执行的节点；如果没有异常，则调用success方法；
     * 最后，无论是否发生异常，都会调用callback方法。
     *
     * @param <T> 流程上下文的类型
     * @param chain 流程链对象
     * @param context 流程上下文对象
     * @throws Exception 如果在执行过程中抛出非SkipException的异常，则最终会抛出该异常
     */
    @SneakyThrows
    public <T> void execute(FlowChain<T> chain, T context) {
        Exception exception = null;
        int index = -1;
        List<FlowNode<T>> nodes = chain.getNodes();

        // 执行每个节点的process方法
        for (FlowNode<T> node : nodes) {
            try {
                node.process(context);
                // 如果节点是SubFlowNode，则递归执行子流程（注释掉的代码）
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

        // 如果有异常发生，执行回滚逻辑
        if (exception != null) {
            for (int i = index; i >= 0; i--) {
                FlowNode<T> node = nodes.get(i);
                try {
                    node.rollback(context, exception);
                } catch (Exception e) {
                    CommonLog.error("rollback执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
                }
            }
        } else {
            // 如果没有异常发生，执行成功回调逻辑
            for (int i = index; i >= 0; i--) {
                FlowNode<T> node = nodes.get(i);
                try {
                    node.success(context);
                } catch (Exception e) {
                    CommonLog.error("success 执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
                }
            }
        }

        // 无论是否有异常发生，都执行最终回调逻辑
        for (int i = index; i >= 0; i--) {
            FlowNode<T> node = nodes.get(i);
            try {
                node.callback(context, exception);
            } catch (Exception e) {
                CommonLog.error("callback执行异常,忽略 name:{}", node.getClass().getSimpleName(), e);
            }
        }

        // 如果有异常发生，重新抛出异常
        if (exception != null) {
            throw exception;
        }
    }
}
