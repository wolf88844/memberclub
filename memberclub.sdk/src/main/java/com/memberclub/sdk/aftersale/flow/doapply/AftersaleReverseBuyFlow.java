/**
 * @(#)AftersaleReverseBuyFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.doapply;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleReverseBuyFlow extends FlowNode<AfterSaleApplyContext> {

    @Override
    public void process(AfterSaleApplyContext context) {
        CommonLog.info("开始逆向提单流程");
    }
}