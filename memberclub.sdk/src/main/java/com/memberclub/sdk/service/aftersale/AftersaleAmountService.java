/**
 * @(#)AftersaleItemService.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.aftersale;

import com.memberclub.domain.dataobject.aftersale.AftersaleUnableCode;
import com.memberclub.domain.dataobject.aftersale.ItemUsage;
import com.memberclub.domain.dataobject.aftersale.RefundTypeEnum;
import com.memberclub.domain.dataobject.aftersale.RefundWayEnum;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.dataobject.aftersale.preview.UsageTypeEnum;
import com.memberclub.domain.facade.AssetDO;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleAmountService {

    public ItemUsage summingPrice(List<AssetDO> assets) {
        // TODO: 2024/12/22
        ItemUsage itemUsage = new ItemUsage();
        int usedPriceFen = assets.stream()
                .filter(AssetDO::isUsed)
                .collect(Collectors.summingInt(AssetDO::getPriceFen));
        int totalPriceFen = assets.stream()
                .filter(AssetDO::isNormal)
                .collect(Collectors.summingInt(AssetDO::getPriceFen));


        itemUsage.setUsedPrice(usedPriceFen);
        itemUsage.setTotalPrice(totalPriceFen);
        return itemUsage;
    }

    public int unusePriceDividePayPriceToCacluateRefundPrice(int payPriceFen, Map<String, ItemUsage> itemUsageMap) {
        int usedPriceFen = 0;

        int totalPriceFen = 0;
        for (Map.Entry<String, ItemUsage> entry : itemUsageMap.entrySet()) {
            usedPriceFen += entry.getValue().getUsedPrice();
            totalPriceFen += entry.getValue().getTotalPrice();
        }
        int unusePriceFen = totalPriceFen - usedPriceFen;

        BigDecimal unuse = new BigDecimal(unusePriceFen);
        BigDecimal total = new BigDecimal(totalPriceFen);
        BigDecimal pay = new BigDecimal(payPriceFen);

        int recommendRefundPrice = unuse.divide(total).multiply(pay).intValue();
        return recommendRefundPrice;
    }

    public void calculateUsageTypeByAmount(AftersalePreviewContext context) {
        if (context.getRecommendRefundPrice() < 0) {
            AftersaleUnableCode.INTERNAL_ERROR.throwException("退款金额内部计算错误", null);
        } else if (context.getRecommendRefundPrice() == 0) {
            context.setUsageType(UsageTypeEnum.USEOUT);
            AftersaleUnableCode.USE_OUT_ERROR.throwException();
        } else if (context.getRecommendRefundPrice() > 0 && context.getRecommendRefundPrice() < context.getPayPriceFen()) {
            context.setUsageType(UsageTypeEnum.USED);
            context.setRefundType(RefundTypeEnum.PORTION_RFUND);
        } else if (context.getRecommendRefundPrice() == context.getPayPriceFen()) {
            context.setUsageType(UsageTypeEnum.UNUSE);
            context.setRefundType(RefundTypeEnum.ALL_REFUND);
        } else {
            AftersaleUnableCode.INTERNAL_ERROR.throwException("退款金额内部计算错误", null);
        }
    }

    public RefundWayEnum calculateRefundWaySupportPortionRefund(AftersalePreviewContext context) {
        if (context.getUsageType() == UsageTypeEnum.USED || context.getUsageType() == UsageTypeEnum.UNUSE) {
            return RefundWayEnum.ORDER_BACKSTRACK;
        }
        AftersaleUnableCode.INTERNAL_ERROR.throwException("不应该走到这里,已用尽应在上层拦截", null);
        return null;
    }

    public RefundWayEnum calculateRefundWayUnSupportPortionRefund(AftersalePreviewContext context) {
        if (context.getUsageType() == UsageTypeEnum.UNUSE) {
            return RefundWayEnum.ORDER_BACKSTRACK;
        } else if (context.getUsageType() == UsageTypeEnum.USED) {
            return RefundWayEnum.CUSTOMER_REFUND;
        }
        AftersaleUnableCode.INTERNAL_ERROR.throwException("不应该走到这里,已用尽应在上层拦截", null);
        return null;
    }
}