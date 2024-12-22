/**
 * @(#)OverallCheckUsageTypeFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.aftersale;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.dataobject.aftersale.preview.UsageTypeCalculateTypeEnum;
import com.memberclub.sdk.extension.aftersale.preview.AftersaleAmountExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class OverallCheckUsageFlow extends FlowNode<AftersalePreviewContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(AftersalePreviewContext context) {
        CommonLog.info("计算使用类型: calculateType:{}, 推荐退款金额:{}, 实付金额:{}",
                context.getUsageTypeCalculateType(),
                context.getRecommendRefundPrice(),
                context.getPayPriceFen());

        if (context.getUsageTypeCalculateType() == UsageTypeCalculateTypeEnum.USE_AMOUNT) {
            extensionManager.getExtension(context.toDefaultBizScene(),
                    AftersaleAmountExtension.class).calculateUsageTypeByAmount(context);
        } else if (context.getUsageTypeCalculateType() == UsageTypeCalculateTypeEnum.USE_STATUS) {

        }
    }
}