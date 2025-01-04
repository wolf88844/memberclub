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
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.sdk.perform.extension.execute.MemberSubOrderPerformExtension;
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
        for (SubOrderPerformContext subOrderPerformContext : context.getSubOrderPerformContexts()) {
            MemberSubOrderDO his = subOrderPerformContext.getSubOrder();
            SubOrderExtraInfo extraInfo =
                    extensionManager.getExtension(context.toDefaultScene(),
                            MemberSubOrderPerformExtension.class).toCommonExtraInfo(context, subOrderPerformContext);
            his.setExtra(extraInfo);
        }
    }
}