/**
 * @(#)ExtractMemberOrderSkuDetailsFlow.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.build;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class ExtractMemberOrderSkuDetailsFlow extends FlowNode<PerformContext> {

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(PerformContext context) {
        List<SkuInfoDO> skuBuyDetails = performDomainService.extractSkuBuyDetail(context.getMemberOrder());
        context.setSkuBuyDetails(skuBuyDetails);

        MemberOrderExtraInfo extraInfo = performDomainService.extractExtraInfO(context.getMemberOrder());
        context.setMemberOrderExtraInfo(extraInfo);
        context.setUserInfo(extraInfo.getUserInfo());
    }
}