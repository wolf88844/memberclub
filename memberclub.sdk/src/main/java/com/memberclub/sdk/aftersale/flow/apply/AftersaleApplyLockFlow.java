/**
 * @(#)AftersaleApplyLockFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.apply;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.exception.AftersaleDoApplyException;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleApplyLockFlow extends FlowNode<AfterSaleApplyContext> {

    @Override
    public void process(AfterSaleApplyContext context) {
        // TODO: 2025/1/1
        //加锁失败,抛出异常
    }

    @Override
    public void callback(AfterSaleApplyContext context, Exception e) {
        if (e != null) {
            // TODO: 2025/1/1 如果异常类型是受理异常,则不能释放锁.
            if (e instanceof AftersaleDoApplyException) {

            }
        } else {
            // TODO: 2025/1/1 释放锁
        }
    }

    @Override
    public void rollback(AfterSaleApplyContext context, Exception e) {


    }
}