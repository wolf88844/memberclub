/**
 * @(#)OfflineStatatisticsUsageAmountFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.aftersale;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 * 离线统计的方式计算使用金额
 */
@Service
public class OfflineStatatisticsUsageAmountFlow extends FlowNode<AftersalePreviewContext> {

    @Override
    public void process(AftersalePreviewContext context) {

    }
}