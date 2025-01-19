/**
 * @(#)MemberOrderDomainService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.domain;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.trade.MemberOrder;
import com.memberclub.domain.entity.trade.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PurchaseConvertor;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberSubOrderDao;
import com.memberclub.sdk.memberorder.extension.MemberOrderDomainExtension;
import com.memberclub.sdk.sku.service.MemberOrderDataObjectBuildFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.memberclub.domain.common.MemberTradeEvent.MEMBER_ORDER_START_PERFORM;

/**
 * author: 掘金五阳
 */
@DS("tradeDataSource")
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

    @Transactional(rollbackFor = Exception.class)
    public Integer onStartPerform(PerformContext context) {
        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, context.getUserId())
                .eq(MemberOrder::getTradeId, context.getTradeId())
                .lt(MemberOrder::getPerformStatus, MEMBER_ORDER_START_PERFORM.getToStatus())
                .set(MemberOrder::getPerformStatus, MEMBER_ORDER_START_PERFORM.getToStatus())
                .set(MemberOrder::getUtime, TimeUtil.now());

        int cnt = extensionManager.getExtension(BizScene.of(context.getBizType()),
                MemberOrderDomainExtension.class).onStartPerform(context, wrapper);
        return cnt;
    }

    @Transactional(rollbackFor = Exception.class)
    public void onPerformSuccess(PerformContext context, MemberOrderDO order) {
        order.onPerformSuccess(context);

        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, order.getUserId())
                .eq(MemberOrder::getTradeId, order.getTradeId())
                .set(MemberOrder::getStatus, order.getStatus().getCode())
                .set(MemberOrder::getPerformStatus, order.getPerformStatus().getCode())
                .set(MemberOrder::getStime, order.getStime())
                .set(MemberOrder::getEtime, order.getEtime())
                .set(MemberOrder::getUtime, order.getUtime())
        ;

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                MemberOrderDomainExtension.class).onPerformSuccess(context, order, wrapper);
    }


    @Retryable
    @Transactional(rollbackFor = Exception.class)
    public void submitFail(MemberOrderDO order) {
        // TODO: 2025/1/4

    }

    @Transactional(rollbackFor = Exception.class)
    public void onReversePerformSuccess(ReversePerformContext context) {
        MemberOrderDO order = context.getMemberOrderDO();
        order.onReversePerformSuccess(context);

        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, order.getUserId())
                .eq(MemberOrder::getTradeId, order.getTradeId())
                .set(MemberOrder::getPerformStatus, order.getPerformStatus().getCode())
                .set(MemberOrder::getUtime, order.getUtime())
        ;

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                MemberOrderDomainExtension.class).onReversePerformSuccess(context, order, wrapper);
    }

    public MemberOrderDO getMemberOrderDO(long userId, String tradeId) {
        MemberOrder order = memberOrderDao.selectByTradeId(userId, tradeId);

        List<MemberSubOrder> subOrders = memberSubOrderDao.selectByTradeId(userId, tradeId);

        MemberOrderDO memberOrderDO = memberOrderDataObjectBuildFactory.buildMemberOrderDO(order, subOrders);

        return memberOrderDO;
    }
}