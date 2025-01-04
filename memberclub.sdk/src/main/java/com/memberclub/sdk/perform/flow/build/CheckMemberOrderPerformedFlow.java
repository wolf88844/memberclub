/**
 * @(#)CheckMemberOrderPerformedFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.RetrySourceEunm;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.purchase.service.domain.MemberOrderDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class CheckMemberOrderPerformedFlow extends FlowNode<PerformContext> {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private MemberSubOrderDao performHisDao;

    @Autowired
    private MemberOrderDomainService memberOrderDomainService;


    @Override
    public void process(PerformContext context) {
        MemberOrderDO memberOrder = memberOrderDomainService.getMemberOrderDO(context.getUserId(), context.getTradeId());
        context.setMemberOrder(memberOrder);
        if (context.getRetrySource() == RetrySourceEunm.UPSTREAM_RETRY) {
            context.setSkipPerform(true);
        }

        if (context.getRetrySource() == RetrySourceEunm.SELF_RETRY) {
            if (memberOrder.isPerformed()) {
                context.setSkipPerform(true);
            }
            if (!MemberOrderStatusEnum.isPerformEnabled(memberOrder.getStatus().getCode())) {
                CommonLog.error("当前履约状态不允许再次履约 status", memberOrder.getStatus());
                Monitor.PERFORM_EXECUTE.counter(context.getBizType().getCode(), "curr_status_cant_perform", memberOrder.getStatus());
                throw ResultCode.CAN_NOT_PERFORM_RETRY.newException();
            }
        }

        if (context.isSkipPerform() && memberOrder != null) {
            // TODO: 2024/12/15 构建返回值
        }

        if (memberOrder != null && !context.isSkipPerform()) {
            List<MemberSubOrder> hisList = performHisDao.selectByTradeId(context.getUserId(), context.getTradeId());
            context.setHisListFromDb(hisList);
        }
    }
}