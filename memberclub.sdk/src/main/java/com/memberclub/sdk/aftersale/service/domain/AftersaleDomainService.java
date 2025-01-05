/**
 * @(#)AftersaleDomainService.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.service.domain;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderExtraDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderStatusEnum;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.AftersaleOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.AftersaleConvertor;
import com.memberclub.infrastructure.mybatis.mappers.AftersaleOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.common.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleDomainService {

    @Autowired
    private AftersaleOrderDao aftersaleOrderDao;


    public AftersaleOrderDO generateOrder(AfterSaleApplyContext context) {
        AftersaleOrderDO order = AftersaleConvertor.INSTANCE.toAftersaleOrderDO(context.getCmd());
        order.setActPayPriceFen(context.getPreviewContext().getPayPriceFen());
        order.setActRefundPriceFen(context.getPreviewContext().getActRefundPrice());
        order.setApplySkuDetails(Lists.newArrayList());
        order.setStatus(AftersaleOrderStatusEnum.INIT);
        order.setCtime(TimeUtil.now());
        order.setExtra(new AftersaleOrderExtraDO());
        order.setRefundType(context.getPreviewContext().getRefundType());
        return order;
    }

    @Transactional
    public AftersaleOrder insertOrder(AftersaleOrder order) {
        int cnt = aftersaleOrderDao.insertIgnoreBatch(ImmutableList.of(order));

        if (cnt < 1) {
            AftersaleOrder orderFromDb = aftersaleOrderDao.queryById(order.getUserId(), order.getId());
            if (orderFromDb != null) {
                CommonLog.warn("新增售后单幂等成功 orderFromDb:{}, orderNew:{}", orderFromDb, order);
                Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(), "insert", "duplicated");
                return orderFromDb;
            } else {
                CommonLog.error("新增售后单失败  orderNew:{}", order);
                Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(), "insert", "error");
                throw ResultCode.DATA_UPDATE_ERROR.newException("新增售后单失败");
            }
        } else {
            CommonLog.info("新增售后单成功:{}", order);
            Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(), "insert", "succ");
        }
        return order;
    }

    @Transactional
    public void completeAftersaleOrder(AftersaleOrder order) {
        // TODO: 2025/1/1
        int cnt = aftersaleOrderDao.updateStatus2Succ(order.getUserId(),
                order.getId(),
                AftersaleOrderStatusEnum.AFTERSALE_SUCC.getCode(),
                TimeUtil.now());
        if (cnt < 1) {
            AftersaleOrder orderFromDb = aftersaleOrderDao.queryById(order.getUserId(), order.getId());
            if (order != null && AftersaleOrderStatusEnum.AFTERSALE_SUCC.isEquals(order.getStatus())) {
                CommonLog.warn("修改售后单为成功态,幂等成功 order:{}", order);
                Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(),
                        "updateStatus2Succ", "duplicated");
            } else {
                CommonLog.warn("修改售后单为成功态, 失败 order:{}", order);
                Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(),
                        "updateStatus2Succ", "error");
                throw ResultCode.DATA_UPDATE_ERROR.newException("更新售后单为成功态异常");
            }
        } else {
            CommonLog.warn("修改售后单为成功态成功 order:{}", order);
            Monitor.AFTER_SALE_DOAPPLY.counter(order.getBizType(),
                    "updateStatus2Succ", "succ");
        }
    }

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    public void onRefundSuccessForMemberOrder(AfterSaleApplyContext context) {
        MemberOrderDO memberOrder = context.getPreviewContext().getMemberOrder();
        memberOrder.onRefundSuccess(context);
        memberOrderDao.updateStatus2RefundSuccess(memberOrder.getUserId(),
                memberOrder.getTradeId(),
                memberOrder.getStatus().getCode(),
                TimeUtil.now()
        );

        CommonLog.info("修改主单的主状态为{}", memberOrder.getStatus());
        for (MemberSubOrderDO subOrder : context.getPreviewContext().getSubOrders()) {
            memberSubOrderDao.updateStatusOnRefundSuccess(subOrder.getUserId(),
                    subOrder.getSubTradeId(),
                    subOrder.getStatus().getCode(),
                    JsonUtils.toJson(subOrder.getExtra()),
                    TimeUtil.now()
            );

            CommonLog.info("修改子单的主状态为{}", subOrder.getStatus());
        }
    }
}