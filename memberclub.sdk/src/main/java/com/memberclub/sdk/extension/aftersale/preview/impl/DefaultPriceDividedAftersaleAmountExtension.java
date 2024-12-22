/**
 * @(#)PriceDividedAftersaleAmountExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.aftersale.ItemUsage;
import com.memberclub.domain.dataobject.aftersale.RefundWayEnum;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.extension.aftersale.preview.AftersaleAmountExtension;
import com.memberclub.sdk.service.aftersale.AftersaleAmountService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "价格除法计算售后金额", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultPriceDividedAftersaleAmountExtension implements AftersaleAmountExtension {

    @Autowired
    private AftersaleAmountService aftersaleAmountService;

    @Override
    public int calculteRecommendRefundPrice(AftersalePreviewContext context, Map<String, ItemUsage> batchCode2ItemUsageMap) {
        return aftersaleAmountService.unusePriceDividePayPriceToCacluateRefundPrice(context.getPayPriceFen(), batchCode2ItemUsageMap);
    }

    @Override
    public void calculateUsageTypeByAmount(AftersalePreviewContext context) {
        aftersaleAmountService.calculateUsageTypeByAmount(context);
    }

    @Override
    public RefundWayEnum calculateRefundWay(AftersalePreviewContext context) {
        return aftersaleAmountService.calculateRefundWaySupportPortionRefund(context);
    }
}