/**
 * @(#)CheckMemberOrderPerformedFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.domain.exception.ResultCode;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.monitor.Monitor;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.common.RetrySourceEunm;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class CheckMemberOrderPerformedFlow extends FlowNode<PerformContext> {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Override
    public void process(PerformContext context) {

        MemberOrder memberOrder = null;
        memberOrder = memberOrderDao.selectByTradeId(context.getUserId(), context.getTradeId());
        context.setMemberOrder(memberOrder);
        if (context.getRetrySource() == RetrySourceEunm.UPSTREAM_RETRY) {
            context.setSkipPerform(true);
        }

        if (context.getRetrySource() == RetrySourceEunm.SELF_RETRY) {
            if (memberOrder.getStatus() == MemberOrderStatusEnum.PERFORMED.toInt()) {
                context.setSkipPerform(true);
            }
            if (!MemberOrderStatusEnum.isPerformEnabled(memberOrder.getStatus())) {
                CommonLog.error("当前履约状态不允许再次履约 status", memberOrder.getStatus());
                Monitor.PERFORM.report(context.getBizType().toBizType(), "curr_status_cant_perform_" + memberOrder.getStatus());
                ResultCode.CAN_NOT_PERFORM_RETRY.throwException();
            }
        }

        if (context.isSkipPerform() && memberOrder != null) {
            // TODO: 2024/12/15 构建返回值
        }
    }
}