/**
 * @(#)MemberSubOrderDomainService.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.domain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.sdk.memberorder.extension.MemberSubOrderDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: 掘金五阳
 */
@Service
public class MemberSubOrderDomainService {


    @Autowired
    private ExtensionManager extensionManager;

    public void onSubmitSuccess(MemberOrderDO memberOrderDO) {
        for (MemberSubOrderDO subOrder : memberOrderDO.getSubOrders()) {
            LambdaUpdateWrapper<MemberSubOrder> subOrderWrapper = new LambdaUpdateWrapper<>();
            subOrderWrapper.eq(MemberSubOrder::getUserId, subOrder.getUserId())
                    .eq(MemberSubOrder::getSubTradeId, subOrder.getSubTradeId())
                    .set(MemberSubOrder::getStatus, subOrder.getStatus().getCode())
                    .set(MemberSubOrder::getActPriceFen, subOrder.getActPriceFen())
                    .set(MemberSubOrder::getExtra, JsonUtils.toJson(subOrder.getExtra()))
                    .set(MemberSubOrder::getOrderId, subOrder.getOrderId())
                    .set(MemberSubOrder::getUtime, TimeUtil.now());
            extensionManager.getExtension(BizScene.of(subOrder.getBizType()),
                    MemberSubOrderDomainExtension.class).onSubmitSuccess(subOrder, subOrderWrapper);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void onPerformSuccess(PerformContext performContext,
                                 SubOrderPerformContext subOrderPerformContext,
                                 MemberSubOrderDO subOrder) {
        subOrder.onPerformSuccess(subOrderPerformContext);

        LambdaUpdateWrapper<MemberSubOrder> subOrderWrapper = new LambdaUpdateWrapper<>();
        subOrderWrapper.eq(MemberSubOrder::getUserId, subOrder.getUserId())
                .eq(MemberSubOrder::getSubTradeId, subOrder.getSubTradeId())
                .set(MemberSubOrder::getStatus, subOrder.getStatus().getCode())
                .set(MemberSubOrder::getPerformStatus, subOrder.getPerformStatus().getCode())
                .set(MemberSubOrder::getStime, subOrder.getStime())
                .set(MemberSubOrder::getEtime, subOrder.getEtime())
                .set(MemberSubOrder::getExtra, JsonUtils.toJson(subOrder.getExtra()))
                .set(MemberSubOrder::getUtime, TimeUtil.now());

        extensionManager.getExtension(BizScene.of(subOrder.getBizType()), MemberSubOrderDomainExtension.class)
                .onPerformSuccess(performContext, subOrderPerformContext, subOrder, subOrderWrapper);
    }


}