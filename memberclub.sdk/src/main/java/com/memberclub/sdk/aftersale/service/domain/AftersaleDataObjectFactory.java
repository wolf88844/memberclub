/**
 * @(#)AftersaleDataObjectFactory.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.service.domain;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderExtraDO;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderStatusEnum;
import com.memberclub.domain.entity.AftersaleOrder;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleDataObjectFactory {

    public AftersaleOrderDO buildAftersaleOrderDO(AftersaleOrder order) {
        AftersaleOrderDO orderDO = new AftersaleOrderDO();
        orderDO.setActPayPriceFen(order.getActPayPriceFen());
        orderDO.setActRefundPriceFen(order.getActRefundPriceFen());

        orderDO.setBizType(BizTypeEnum.findByCode(order.getBizType()));
        orderDO.setCtime(order.getCtime());
        orderDO.setExtra(JsonUtils.fromJson(order.getExtra(), AftersaleOrderExtraDO.class));
        orderDO.setApplySkuInfos(orderDO.getExtra().getApplySkus());
        orderDO.setId(order.getId());
        orderDO.setOperator(order.getOperator());
        orderDO.setReason(orderDO.getExtra().getReason());
        orderDO.setRecommendRefundPriceFen(order.getRecommendRefundPriceFen());
        orderDO.setRefundType(RefundTypeEnum.findByCode(order.getRefundType()));
        orderDO.setSource(AftersaleSourceEnum.findByCode(order.getSource()));
        orderDO.setStatus(AftersaleOrderStatusEnum.findByCode(order.getStatus()));
        orderDO.setTradeId(order.getTradeId());
        orderDO.setUserId(order.getUserId());
        orderDO.setUtime(order.getUtime());
        return orderDO;
    }
}