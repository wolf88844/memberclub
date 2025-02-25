/**
 * @(#)MemberOrderDomainService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.domain;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.trade.MemberOrder;
import com.memberclub.domain.entity.trade.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PurchaseConvertor;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberSubOrderDao;
import com.memberclub.sdk.memberorder.MemberOrderDataObjectBuildFactory;
import com.memberclub.sdk.memberorder.extension.MemberOrderDomainExtension;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.memberclub.domain.common.MemberTradeEvent.MEMBER_ORDER_START_PERFORM;

/**
 * MemberOrderDomainService 是一个服务类，用于处理会员订单的业务逻辑。
 * 包含创建、提交成功、取消、执行开始、执行成功、反向执行成功等操作，并提供查询会员订单的方法。
 *
 * @author 掘金五阳
 */
@DS("tradeDataSource")
@Service
public class MemberOrderDomainService {

    /**
     * 注入的DAO层接口，用于操作会员订单数据。
     */
    @Autowired
    private MemberOrderDao memberOrderDao;

    /**
     * 注入的DAO层接口，用于操作会员子订单数据。
     */
    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    /**
     * 扩展管理器，用于获取不同业务场景下的扩展实现。
     */
    @Autowired
    private ExtensionManager extensionManager;

    /**
     * 子订单领域服务，用于处理子订单相关的业务逻辑。
     */
    @Autowired
    private MemberSubOrderDomainService memberSubOrderDomainService;

    /**
     * 构建会员订单数据对象的工厂类。
     */
    @Autowired
    private MemberOrderDataObjectBuildFactory memberOrderDataObjectBuildFactory;

    /**
     * 创建会员订单。
     * 将传入的 {@link MemberOrderDO} 转换为 {@link MemberOrder} 和 {@link MemberSubOrder}，
     * 并批量插入数据库。如果插入失败则抛出异常。
     *
     * @param memberOrderDO 会员订单数据对象
     */
    public void createMemberOrder(MemberOrderDO memberOrderDO) {
        MemberOrder order = PurchaseConvertor.INSTANCE.toMemberOrder(memberOrderDO);

        List<MemberSubOrder> subOrders = memberOrderDO.getSubOrders().stream()
                .map(PurchaseConvertor.INSTANCE::toMemberSubOrder)
                .collect(Collectors.toList());

        int cnt = memberOrderDao.insertIgnoreBatch(Lists.newArrayList(order));
        if (cnt < 1) {
            throw ResultCode.ORDER_CREATE_ERROR.newException("会员单生成失败");
        }

        int subOrderCnt = memberSubOrderDao.insertIgnoreBatch(subOrders);
        if (subOrderCnt < subOrders.size()) {
            throw ResultCode.ORDER_CREATE_ERROR.newException("会员子单生成失败");
        }
        CommonLog.info("生成会员单数据成功");
    }

    /**
     * 处理订单提交成功的业务逻辑。
     * 更新会员订单的状态和其他相关信息，并调用扩展点处理额外逻辑。
     *
     * @param order 会员订单数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Retryable(throwException = false)
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

    /**
     * 处理订单取消的业务逻辑。
     * 更新会员订单的状态和其他相关信息，并调用扩展点处理额外逻辑。
     *
     * @param context 取消上下文
     * @param order   会员订单数据对象
     */
    @Transactional(rollbackFor = Exception.class)
    @Retryable(throwException = false)
    public void onSubmitCancel(PurchaseCancelContext context, MemberOrderDO order) {
        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, order.getUserId())
                .eq(MemberOrder::getTradeId, order.getTradeId())
                .set(MemberOrder::getStatus, order.getStatus().getCode())
                .set(MemberOrder::getExtra, JsonUtils.toJson(order.getExtra()))
                .set(MemberOrder::getUtime, TimeUtil.now());

        extensionManager.getExtension(BizScene.of(order.getBizType()),
                MemberOrderDomainExtension.class).onSubmitCancel(order, wrapper);

        memberSubOrderDomainService.onSubmitCancel(context, order);
    }

    /**
     * 开始执行会员订单的业务逻辑。
     * 更新会员订单的执行状态，并调用扩展点处理额外逻辑。
     *
     * @param context 执行上下文
     * @return 更新影响的行数
     */
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

    /**
     * 处理会员订单执行成功的业务逻辑。
     * 更新会员订单的状态和执行状态，并调用扩展点处理额外逻辑。
     *
     * @param context 执行上下文
     * @param order   会员订单数据对象
     */
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
                .set(MemberOrder::getUtime, order.getUtime());

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                MemberOrderDomainExtension.class).onPerformSuccess(context, order, wrapper);
    }

    /**
     * 处理会员订单提交失败的业务逻辑（暂未实现）。
     *
     * @param order 会员订单数据对象
     */
    @Retryable(throwException = false)
    @Transactional(rollbackFor = Exception.class)
    public void submitFail(MemberOrderDO order) {
        // TODO: 2025/1/4
    }

    /**
     * 处理会员订单反向执行成功的业务逻辑。
     * 更新会员订单的执行状态，并调用扩展点处理额外逻辑。
     *
     * @param context 反向执行上下文
     */
    @Transactional(rollbackFor = Exception.class)
    public void onReversePerformSuccess(ReversePerformContext context) {
        MemberOrderDO order = context.getMemberOrderDO();
        order.onReversePerformSuccess(context);

        LambdaUpdateWrapper<MemberOrder> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberOrder::getUserId, order.getUserId())
                .eq(MemberOrder::getTradeId, order.getTradeId())
                .set(MemberOrder::getPerformStatus, order.getPerformStatus().getCode())
                .set(MemberOrder::getUtime, order.getUtime());

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                MemberOrderDomainExtension.class).onReversePerformSuccess(context, order, wrapper);
    }

    /**
     * 根据用户ID和交易ID查询会员订单。
     *
     * @param userId 用户ID
     * @param tradeId 交易ID
     * @return 查询到的会员订单数据对象，如果未找到则返回null
     */
    public MemberOrderDO getMemberOrderDO(long userId, String tradeId) {
        MemberOrder order = memberOrderDao.selectByTradeId(userId, tradeId);
        if (order == null) {
            return null;
        }

        List<MemberSubOrder> subOrders = memberSubOrderDao.selectByTradeId(userId, tradeId);

        MemberOrderDO memberOrderDO = memberOrderDataObjectBuildFactory.buildMemberOrderDO(order, subOrders);

        return memberOrderDO;
    }

    /**
     * 根据用户ID、交易ID和子交易ID查询会员订单，并过滤出指定子交易ID的子订单。
     *
     * @param userId 用户ID
     * @param tradeId 交易ID
     * @param subTradeId 子交易ID
     * @return 查询到的会员订单数据对象，如果未找到或无匹配子订单则返回null
     */
    public MemberOrderDO getMemberOrderDO(long userId, String tradeId, Long subTradeId) {
        MemberOrderDO memberOrderDO = getMemberOrderDO(userId, tradeId);
        if (memberOrderDO != null && CollectionUtils.isNotEmpty(memberOrderDO.getSubOrders())) {
            memberOrderDO.setSubOrders(
                    memberOrderDO.getSubOrders().stream()
                            .filter(sbo -> NumberUtils.compare(sbo.getSubTradeId(), subTradeId) == 0)
                            .collect(Collectors.toList())
            );
        }

        return memberOrderDO;
    }
}
