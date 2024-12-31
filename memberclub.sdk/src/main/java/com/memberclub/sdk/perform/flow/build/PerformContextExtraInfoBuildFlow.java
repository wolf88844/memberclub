/**
 * @(#)PerformContextCommonPropertyBuildFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformHisDO;
import com.memberclub.domain.dataobject.perform.his.PerformHisExtraInfo;
import com.memberclub.sdk.perform.extension.execute.MemberPerformHisExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PerformContextExtraInfoBuildFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Override
    public void process(PerformContext context) {
        for (SkuPerformContext skuPerformContext : context.getSkuPerformContexts()) {
            MemberPerformHisDO his = skuPerformContext.getHis();
            PerformHisExtraInfo extraInfo =
                    extensionManager.getExtension(context.toDefaultScene(),
                            MemberPerformHisExtension.class).toCommonExtraInfo(context, skuPerformContext);
            his.setExtra(extraInfo);
        }
    }
}