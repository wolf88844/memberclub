/**
 * @(#)PerformItemGrantFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.memberclub.common.exception.ResultCode;
import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.execute.ItemGroupGrantResult;
import com.memberclub.sdk.extension.perform.execute.PerformItemGrantExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PerformItemGrantFlow extends FlowNode<PerformItemContext> {

    @Autowired
    private ExtensionManger extensionManger;

    @Override
    public void process(PerformItemContext context) {
        String scene = extensionManger.getSceneExtension(context.getPerformContext().toDefaultScene())
                .buildPerformItemGrantExtensionScene(context);
        
        PerformItemGrantExtension extension =
                extensionManger.getExtension(BizScene.of(context.getPerformContext().getBizType().toBizType(), scene),
                        PerformItemGrantExtension.class);
        ItemGroupGrantResult result = extension.grant(context, context.getItems());
        if (!result.isSuccess()) {
            ResultCode.PERFORM_ITEM_GRANT_ERROR.throwException();
        }
    }
}