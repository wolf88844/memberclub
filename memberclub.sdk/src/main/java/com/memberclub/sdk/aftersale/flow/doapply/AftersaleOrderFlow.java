/**
 * @(#)AftersaleOrderFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.doapply;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.domain.entity.AftersaleOrder;
import com.memberclub.infrastructure.mapstruct.AftersaleConvertor;
import com.memberclub.sdk.aftersale.service.domain.AftersaleDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleOrderFlow extends FlowNode<AfterSaleApplyContext> {

    @Autowired
    private AftersaleDomainService aftersaleDomainService;

    @Override
    public void process(AfterSaleApplyContext context) {
        AftersaleOrderDO orderDO = context.getAftersaleOrderDO();

        AftersaleOrder order = AftersaleConvertor.INSTANCE.toAftersaleOrder(orderDO);

        order = aftersaleDomainService.insertOrder(order);
        context.setOrder(order);
    }

    @Override
    public void success(AfterSaleApplyContext context) {
        aftersaleDomainService.completeOrder(context.getOrder());
    }

    @Override
    public void rollback(AfterSaleApplyContext context) {
    }
}