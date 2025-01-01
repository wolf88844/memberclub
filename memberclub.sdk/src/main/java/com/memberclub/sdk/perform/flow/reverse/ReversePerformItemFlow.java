/**
 * @(#)ReversePerformItemFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.reverse;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.flow.SkipException;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.perform.reverse.PerformItemReverseInfo;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class ReversePerformItemFlow extends FlowNode<ReversePerformContext> {

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(ReversePerformContext context) {
        List<PerformItemReverseInfo> items = context.getCurrentPerformHisReverseInfo().getCurrentItems();
        if (items == null) {
            items = context.getCurrentPerformHisReverseInfo().getItems();
            context.getCurrentPerformHisReverseInfo().setCurrentItems(items);
        }

        if (performDomainService.isFinishReverseMemberPerformItems(context,
                context.getCurrentPerformHisReverseInfo(), items)) {
            CommonLog.warn("已经完成逆向履约,无需再次重试");
            throw new SkipException("完成逆向履约,无需再次重试");
        }

        performDomainService.startReverseMemberPerformItems(context, context.getCurrentPerformHisReverseInfo(), items);
    }

    @Override
    public void success(ReversePerformContext context) {
        performDomainService.finishReverseMemberPerformItems(context,
                context.getCurrentPerformHisReverseInfo(),
                context.getCurrentPerformHisReverseInfo().getCurrentItems());
    }
}