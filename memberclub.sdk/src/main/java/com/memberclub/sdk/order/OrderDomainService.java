/**
 * @(#)OrderDomainService.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.order;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.facade.CommonOrderSubmitResult;
import com.memberclub.domain.dataobject.purchase.facade.SkuBuyResultDO;
import com.memberclub.sdk.purchase.extension.CommonOrderExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OrderDomainService {
    @Autowired
    private ExtensionManager extensionManager;

    public void submitOrder(PurchaseSubmitContext context) {
        CommonOrderExtension extension = extensionManager.getExtension(
                context.toDefaultBizScene(), CommonOrderExtension.class);

        extension.onPreSubmit(context);

        CommonOrderSubmitResult result = extension.doSubmit(context);
        context.getMemberOrder().getOrderInfo().setOrderId(result.getOrderId());
        context.getMemberOrder().setActPriceFen(result.getActPriceFen());
        for (SkuBuyResultDO skuBuyResult : result.getSkuBuyResults()) {
            for (MemberSubOrderDO subOrder : context.getMemberOrder().getSubOrders()) {
                if (skuBuyResult.getSkuId() == subOrder.getSkuId()) {
                    subOrder.setOrderId(result.getOrderId());
                    subOrder.setActPriceFen(skuBuyResult.getActPriceFen());
                }
            }
        }
    }
}