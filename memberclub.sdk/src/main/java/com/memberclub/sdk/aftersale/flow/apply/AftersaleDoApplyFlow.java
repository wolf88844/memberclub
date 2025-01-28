/**
 * @(#)AftersaleDoApplyFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.apply;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.exception.AftersaleDoApplyException;
import com.memberclub.sdk.aftersale.extension.apply.AfterSaleApplyExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memberclub.domain.exception.ResultCode.AFTERSALE_DO_APPLY_ERROR;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleDoApplyFlow extends FlowNode<AfterSaleApplyContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Retryable(maxTimes = 5, initialDelaySeconds = 1, maxDelaySeconds = 30, throwException = true)
    @Override
    public void process(AfterSaleApplyContext context) {
        try {
            extensionManager.getExtension(context.toBizScene(),
                    AfterSaleApplyExtension.class).doApply(context);
        } catch (Exception e) {
            CommonLog.error("售后受理流程异常 context:{}", context, e);
            throw new AftersaleDoApplyException(AFTERSALE_DO_APPLY_ERROR, e);
        }
    }
}