/**
 * @(#)StartPerformUpdteMemberOrderFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.memberclub.domain.common.MemberTradeEvent.MEMBER_ORDER_START_PERFORM;

/**
 * author: 掘金五阳
 */
@Service
public class StartPerformUpdteMemberOrderFlow extends FlowNode<PerformContext> {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Override
    public void process(PerformContext context) {
        int count = memberOrderDao.updateStatus(context.getUserId(),
                context.getTradeId(),
                MEMBER_ORDER_START_PERFORM.getToStatus(),
                MEMBER_ORDER_START_PERFORM.getFromStatus(),
                TimeUtil.now());
        context.setMemberOrderStartPerformUpdateCount(count);
    }
}