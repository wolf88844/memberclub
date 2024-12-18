/**
 * @(#)ExtractMemberOrderSkuDetailsFlow.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.build;

import com.fasterxml.jackson.core.type.TypeReference;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class ExtractMemberOrderSkuDetailsFlow extends FlowNode<PerformContext> {

    @Override
    public void process(PerformContext context) {
        List<SkuBuyDetailDO> skuBuyDetails = JsonUtils.fromJson(context.getMemberOrder().getSkuDetails()
                , new TypeReference<List<SkuBuyDetailDO>>() {
                });
        context.setSkuBuyDetails(skuBuyDetails);
    }
}