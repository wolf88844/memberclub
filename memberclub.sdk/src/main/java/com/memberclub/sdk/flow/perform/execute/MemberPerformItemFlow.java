/**
 * @(#)MemberPerformItemFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.extension.perform.execute.MemberPerformItemExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformItemFlow extends FlowNode<PerformItemContext> {

    @Autowired
    ExtensionManger extensionManger;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Override

    public void process(PerformItemContext context) {
        MemberPerformItemExtension extension =
                extensionManger.getExtension(context.getPerformContext().toDefaultScene(), MemberPerformItemExtension.class);
        List<MemberPerformItem> items = extension.toMemberPerformItems(context);
        int count = memberPerformItemDao.insertIgnoreBatch(items);

    }

    @Override
    public void success(PerformItemContext context) {
        super.success(context);
    }
}