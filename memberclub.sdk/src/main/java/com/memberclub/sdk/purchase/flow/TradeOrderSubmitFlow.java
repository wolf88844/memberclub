/**
 * @(#)TradeOrderSubmitFlow.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class TradeOrderSubmitFlow extends FlowNode<PurchaseSubmitContext> {

    @Override
    public void process(PurchaseSubmitContext context) {
        CommonLog.info("订单提交成功");
    }
}