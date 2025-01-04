/**
 * @(#)MemberOrderBuildFactory.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.google.common.collect.Lists;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.purchase.OrderInfoDO;
import com.memberclub.infrastructure.id.IdGenerator;
import com.memberclub.infrastructure.id.IdTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class MemberOrderBuildFactory {

    @Autowired
    private IdGenerator idGenerator;

    public MemberOrderDO build(PurchaseSubmitContext context) {
        MemberOrderDO order = new MemberOrderDO();
        order.setBizType(context.getBizType());
        order.setCtime(TimeUtil.now());
        order.setUtime(TimeUtil.now());
        order.setLocationInfo(context.getSubmitCmd().getLocationInfo());
        order.setOrderInfo(new OrderInfoDO());
        order.setTradeId(idGenerator.generateId(IdTypeEnum.PURCHASE_TRADE).toString());

        List<MemberSubOrderDO> subOrders = Lists.newArrayList();
        order.setSubOrders(subOrders);
        for (SkuInfoDO skuInfo : context.getSkuInfos()) {
            MemberSubOrderDO subOrder = new MemberSubOrderDO();
            subOrders.add(subOrder);
            subOrder.setBizType(context.getBizType());
            subOrder.setBuyCount(skuInfo.getBuyCount());
            subOrder.setCtime(TimeUtil.now());
            subOrder.setExtra(new SubOrderExtraInfo());
            subOrder.setSkuId(skuInfo.getSkuId());
            subOrder.setTradeId(order.getTradeId());
            subOrder.setUserId(context.getUserId());
            subOrder.setUtime(TimeUtil.now());
        }


        return order;
    }
}