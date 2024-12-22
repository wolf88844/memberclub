/**
 * @(#)CalculateRefundWayFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.aftersale;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.dataobject.aftersale.RefundWayEnum;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.extension.aftersale.preview.AftersaleAmountExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 * 计算赔付方式
 */
@Service
public class CalculateRefundWayFlow extends FlowNode<AftersalePreviewContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(AftersalePreviewContext context) {
        RefundWayEnum refundWay = extensionManager.getExtension(context.toDefaultBizScene(), AftersaleAmountExtension.class)
                .calculateRefundWay(context);
        context.setRefundWay(refundWay);
        CommonLog.info("售后赔付方式:{}", refundWay.toString());
    }
}