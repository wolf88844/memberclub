/**
 * @(#)MemberOrderBuildFactory.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberOrderBuildFactory {

    public MemberOrderDO build(PurchaseSubmitContext context) {
        MemberOrderDO order = new MemberOrderDO();
        order.setBizType(context.getBizType());



        return order;
    }
}