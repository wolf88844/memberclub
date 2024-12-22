/**
 * @(#)AftersalePreviewDegradeFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.aftersale;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.dataobject.aftersale.AftersaleUnableCode;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.config.SwitchEnum;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersalePreviewDegradeFlow extends FlowNode<AftersalePreviewContext> {


    @Override
    public void process(AftersalePreviewContext context) {
        
        boolean degrade = SwitchEnum.AFTERSALE_DEGRADE.getBoolean(context.getCmd().getBizType().toBizType(),
                String.valueOf(context.getCmd().getSource().toInt()));
        if (degrade) {
            CommonLog.warn("渠道{} 已经降级,不能发起售后", context.getCmd().getSource().toString());
            AftersaleUnableCode.DEGRADE_AFTERSALE_ERROR.throwException(null, null);
        }
    }
}