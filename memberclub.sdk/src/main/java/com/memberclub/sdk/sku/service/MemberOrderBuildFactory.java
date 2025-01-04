/**
 * @(#)MemberOrderBuildFactory.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.perform.common.MemberOrderPerformStatusEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.context.purchase.common.RenewTypeEnum;
import com.memberclub.domain.context.purchase.common.SubOrderStatusEnum;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.order.MemberOrderSaleInfo;
import com.memberclub.domain.dataobject.order.MemberOrderSettleInfo;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.his.SubOrderSaleInfo;
import com.memberclub.domain.dataobject.perform.his.SubOrderSettleInfo;
import com.memberclub.domain.dataobject.perform.his.SubOrderViewInfo;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.purchase.OrderInfoDO;
import com.memberclub.infrastructure.id.IdGenerator;
import com.memberclub.infrastructure.id.IdTypeEnum;
import com.memberclub.infrastructure.mapstruct.PurchaseConvertor;
import com.memberclub.sdk.purchase.extension.PurchaseOrderBuildExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class MemberOrderBuildFactory {

    @Autowired
    private IdGenerator idGenerator;

    @Autowired
    private ExtensionManager extensionManager;

    public void onSubmitSuccess(PurchaseSubmitContext context) {
        context.getMemberOrder().onSubmitSuccess(context);
        extensionManager.getExtension(context.toDefaultBizScene(),
                PurchaseOrderBuildExtension.class).onSubmitSuccess(context.getMemberOrder(), context);

    }


    public void onSubmitFail(PurchaseSubmitContext context, Exception e) {
        context.getMemberOrder().onSubmitFail(context);
        extensionManager.getExtension(context.toDefaultBizScene(),
                PurchaseOrderBuildExtension.class).onSubmitFail(context.getMemberOrder(), context, e);
    }


    public MemberOrderDO build(PurchaseSubmitContext context) {
        MemberOrderDO order = new MemberOrderDO();
        order.setBizType(context.getBizType());
        order.setCtime(TimeUtil.now());
        order.setUtime(TimeUtil.now());
        order.setLocationInfo(context.getSubmitCmd().getLocationInfo());
        order.setOrderInfo(new OrderInfoDO());
        order.setTradeId(idGenerator.generateId(IdTypeEnum.PURCHASE_TRADE).toString());
        order.setUserId(context.getUserId());
        order.setUserInfo(context.getUserInfo());
        order.setStatus(MemberOrderStatusEnum.INIT);
        order.setPerformStatus(MemberOrderPerformStatusEnum.INIT);
        order.setSettleInfo(new MemberOrderSettleInfo());
        order.setSaleInfo(new MemberOrderSaleInfo());
        order.getSaleInfo().setRenewType(RenewTypeEnum.NONE);
        order.setExtra(new MemberOrderExtraInfo());
        order.getExtra().setLocationInfo(order.getLocationInfo());
        order.getExtra().setUserInfo(order.getUserInfo());
        order.getExtra().setSettleInfo(order.getSettleInfo());


        extensionManager.getExtension(context.toDefaultBizScene(),
                PurchaseOrderBuildExtension.class).buildOrder(order, context);

        List<MemberSubOrderDO> subOrders = Lists.newArrayList();
        order.setSubOrders(subOrders);
        for (SkuInfoDO skuInfo : context.getSkuInfos()) {
            MemberSubOrderDO subOrder = new MemberSubOrderDO();
            subOrder.setBizType(context.getBizType());
            subOrder.setBuyCount(skuInfo.getBuyCount());
            subOrder.setCtime(TimeUtil.now());
            subOrder.setExtra(new SubOrderExtraInfo());
            subOrder.setSkuId(skuInfo.getSkuId());
            subOrder.setTradeId(order.getTradeId());
            subOrder.setUserId(context.getUserId());
            subOrder.setUtime(TimeUtil.now());
            SubOrderViewInfo viewInfo = PurchaseConvertor.INSTANCE.toSubOrderViewInfo(skuInfo.getViewInfo());

            SubOrderSettleInfo settleInfo = PurchaseConvertor.INSTANCE.toSubOrderSettleInfo(skuInfo.getSettleInfo());

            SubOrderSaleInfo saleInfo = PurchaseConvertor.INSTANCE.toSubOrderSaleInfo(skuInfo.getSaleInfo());
            subOrder.getExtra().setSettleInfo(settleInfo);
            subOrder.getExtra().setViewInfo(viewInfo);
            subOrder.getExtra().setUserInfo(context.getUserInfo());
            subOrder.getExtra().setSaleInfo(saleInfo);

            subOrder.setStatus(SubOrderStatusEnum.INIT);
            subOrder.setPerformStatus(SubOrderPerformStatusEnum.INIT);
            subOrder.setSubTradeId(idGenerator.generateId(IdTypeEnum.PURCHASE_SUB_TRADE));
            subOrder.setOriginPriceFen(skuInfo.getSaleInfo().getOriginPriceFen());
            subOrder.setSalePriceFen(skuInfo.getSaleInfo().getSalePriceFen());

            extensionManager.getExtension(context.toDefaultBizScene(),
                    PurchaseOrderBuildExtension.class).buildSubOrder(order, subOrder, context, skuInfo);

            subOrders.add(subOrder);
        }

        order.setOriginPriceFen(subOrders.stream().collect(Collectors.summingInt(MemberSubOrderDO::getOriginPriceFen)));
        order.setSalePriceFen(subOrders.stream().collect(Collectors.summingInt(MemberSubOrderDO::getSalePriceFen)));
        return order;
    }
}