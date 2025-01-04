/**
 * @(#)MemberOrderSubmitInitialFlow.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.sdk.purchase.service.domain.MemberOrderDomainService;
import com.memberclub.sdk.sku.service.MemberOrderBuildFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberOrderInitialSubmitFlow extends FlowNode<PurchaseSubmitContext> {

    @Autowired
    private MemberOrderBuildFactory memberOrderBuildFactory;

    @Autowired
    private MemberOrderDomainService memberOrderDomainService;

    @Override
    public void process(PurchaseSubmitContext context) {
        MemberOrderDO memberOrderDO = memberOrderBuildFactory.build(context);

        memberOrderDomainService.createMemberOrder(memberOrderDO);
    }
}