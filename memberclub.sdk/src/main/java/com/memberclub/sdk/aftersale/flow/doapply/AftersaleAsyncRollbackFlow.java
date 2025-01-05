/**
 * @(#)AftersaleAsyncRollbackFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.doapply;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 * 如果流程失败,可以发起异步回滚流程.
 * 可选流程.
 */
@Service
public class AftersaleAsyncRollbackFlow extends FlowNode<AfterSaleApplyContext> {

    @Override
    public void process(AfterSaleApplyContext context) {

    }

    @Override
    public void rollback(AfterSaleApplyContext context, Exception e) {
        // TODO: 2025/1/5 确定是否是最后一次重试,那么就开启回滚
    }
}