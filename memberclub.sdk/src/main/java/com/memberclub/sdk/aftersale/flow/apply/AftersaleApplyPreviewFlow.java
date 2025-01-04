/**
 * @(#)AftersaleApplyPreviewFlow.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.flow.apply;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.infrastructure.mapstruct.AftersaleConvertor;
import com.memberclub.sdk.aftersale.service.AftersaleBizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleApplyPreviewFlow extends FlowNode<AfterSaleApplyContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private AftersaleBizService aftersaleBizService;

    @Override
    public void process(AfterSaleApplyContext context) {
        AfterSalePreviewCmd previewCmd = AftersaleConvertor.INSTANCE.toPreviewCmd(context.getCmd());
        AftersalePreviewContext previewContext = aftersaleBizService.doPreview(previewCmd);
        context.setPreviewContext(previewContext);
    }
}