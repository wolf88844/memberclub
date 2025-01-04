/**
 * @(#)MemberOrderDomainService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.service.domain;

import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PurchaseConvertor;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.sku.service.MemberOrderDataObjectBuildFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class MemberOrderDomainService {


    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    @Autowired
    private MemberOrderDataObjectBuildFactory memberOrderDataObjectBuildFactory;

    public void createMemberOrder(MemberOrderDO memberOrderDO) {
        MemberOrder order = PurchaseConvertor.INSTANCE.toMemberOrder(memberOrderDO);

        List<MemberSubOrder> subOrders = memberOrderDO.getSubOrders().stream()
                .map(PurchaseConvertor.INSTANCE::toMemberSubOrder)
                .collect(Collectors.toList());

        int cnt = memberOrderDao.insert(order);
        if (cnt < 1) {
            throw ResultCode.ORDER_CREATE_ERROR.newException("会员单生成失败");
        }

        int subOrderCnt = memberSubOrderDao.insertIgnoreBatch(subOrders);
        if (subOrderCnt < subOrders.size()) {
            throw ResultCode.ORDER_CREATE_ERROR.newException("会员子单生成失败");
        }
        CommonLog.info("生成会员单数据成功");
    }

    @Transactional
    @Retryable
    public void submitSuccess(MemberOrderDO order) {
        int cnt = memberOrderDao.updateStatusOnSubmitSuccess(order.getUserId(),
                order.getTradeId(),
                order.getStatus().getCode(),
                order.getOrderInfo().getOrderId(),
                order.getActPriceFen(),
                JsonUtils.toJson(order.getExtra()),
                TimeUtil.now());

        for (MemberSubOrderDO subOrder : order.getSubOrders()) {
            memberSubOrderDao.updateStatusOnSubmitSuccess(order.getUserId(),
                    subOrder.getSubTradeId(),
                    subOrder.getPerformStatus().getCode(),
                    subOrder.getOrderId(),
                    subOrder.getActPriceFen(),
                    JsonUtils.toJson(subOrder.getExtra()),
                    TimeUtil.now());
        }
    }

    @Retryable
    @Transactional
    public void submitFail(MemberOrderDO order) {
        // TODO: 2025/1/4

    }


    public MemberOrderDO getMemberOrderDO(long userId, String tradeId) {
        MemberOrder order = memberOrderDao.selectByTradeId(userId, tradeId);

        List<MemberSubOrder> subOrders = memberSubOrderDao.selectByTradeId(userId, tradeId);

        MemberOrderDO memberOrderDO = memberOrderDataObjectBuildFactory.buildMemberOrderDO(order, subOrders);

        return memberOrderDO;
    }
}