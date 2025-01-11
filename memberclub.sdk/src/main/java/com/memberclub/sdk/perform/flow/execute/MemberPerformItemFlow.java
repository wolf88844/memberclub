/**
 * @(#)MemberPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.perform.extension.execute.MemberPerformItemExtension;
import com.memberclub.sdk.perform.service.domain.MemberPerformItemDomainService;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformItemFlow extends FlowNode<PerformItemContext> {

    @Autowired
    ExtensionManager extensionManager;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Autowired
    private PerformDomainService performDomainService;

    @Autowired
    private MemberPerformItemDomainService memberPerformItemDomainService;

    @Override
    public void process(PerformItemContext context) {
        MemberPerformItemExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberPerformItemExtension.class);
        List<MemberPerformItem> items = extension.toMemberPerformItems(context);

        memberPerformItemDomainService.createPerformItems(context, items);


    }

    @Override
    public void success(PerformItemContext context) {
        //下游保证批量发放成功
        memberPerformItemDomainService.onPerformStatus(context);
    }
}