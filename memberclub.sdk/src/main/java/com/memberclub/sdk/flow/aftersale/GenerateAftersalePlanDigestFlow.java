/**
 * @(#)GenerateAftersalePlanDigestFlow.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.aftersale;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.config.SwitchEnum;
import com.memberclub.sdk.extension.aftersale.preview.GenerateAfterSalePlanDigestExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 * 生成售后计划摘要
 */
@Service
public class GenerateAftersalePlanDigestFlow extends FlowNode<AftersalePreviewContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(AftersalePreviewContext context) {
        if (context.getDigestVersion() == null) {
            int version = SwitchEnum.AFTERSALE_PLAN_GENERATE_DIGEST_VERSION.getInt(context.getCmd().getBizType().toBizType());
            context.setDigestVersion(version);
        }

        extensionManager.getExtension(
                BizScene.of(context.getCmd().getBizType().toBizType(), String.valueOf(context.getDigestVersion())),
                GenerateAfterSalePlanDigestExtension.class).generateDigest(context);
    }
}