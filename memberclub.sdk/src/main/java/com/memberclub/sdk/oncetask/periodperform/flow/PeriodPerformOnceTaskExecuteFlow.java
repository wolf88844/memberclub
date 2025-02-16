/**
 * @(#)OnceTaskExecuteOnPeriodPerformFlow.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.periodperform.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.context.oncetask.execute.OnceTaskExecuteContext;
import com.memberclub.domain.context.perform.PerformResp;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.sdk.perform.service.PerformBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PeriodPerformOnceTaskExecuteFlow extends FlowNode<OnceTaskExecuteContext> {

    @Autowired
    private PerformBizService performBizService;

    @Retryable(throwException = false, initialDelaySeconds = 1)
    @Override
    public void process(OnceTaskExecuteContext context) {
        PerformResp performResp = performBizService.periodPerform(context.getOnceTask());
        if (!performResp.isSuccess()) {
            if (performResp.isNeedRetry()) {
                throw ResultCode.PERIOD_PERFORM_EXECUTE_ERROR.newException();
            }
            CommonLog.warn("周期履约失败,无需重试 response:{}, task:{}", performResp, context.getOnceTask());
        } else {
            CommonLog.info("周期履约成功 response:{}, task:{}", performResp, context.getOnceTask());
        }
    }
}