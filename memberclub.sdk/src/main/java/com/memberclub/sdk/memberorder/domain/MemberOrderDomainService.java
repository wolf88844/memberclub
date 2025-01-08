/**
 * @(#)MemberOrderDomainService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.domain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PurchaseConvertor;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.memberorder.extension.MemberOrderDomainExtension;
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
    private ExtensionManager extensionManager;

    @Autowired
    private MemberSubOrderDomainService memberSubOrderDomainService;

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

    @Transactional(rollbackFor = Exception.class)
    @Retryable
    public void onSubmitSuccess(MemberOrderDO order) {
        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, order.getUserId())
                .eq(MemberOrder::getTradeId, order.getTradeId())
                .set(MemberOrder::getStatus, order.getStatus().getCode())
                .set(MemberOrder::getActPriceFen, order.getActPriceFen())
                .set(MemberOrder::getExtra, JsonUtils.toJson(order.getExtra()))
                .set(MemberOrder::getOrderId, order.getOrderInfo().getOrderId())
                .set(MemberOrder::getUtime, TimeUtil.now());

        extensionManager.getExtension(BizScene.of(order.getBizType()),
                MemberOrderDomainExtension.class).onSubmitSuccess(order, wrapper);

        memberSubOrderDomainService.onSubmitSuccess(order);
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