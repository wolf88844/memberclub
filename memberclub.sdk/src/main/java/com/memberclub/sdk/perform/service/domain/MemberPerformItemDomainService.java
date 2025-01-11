/**
 * @(#)MemberPerformItemDomainService.java, 一月 11, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.sdk.perform.extension.execute.MemberPerformItemDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class MemberPerformItemDomainService {

    @Autowired
    private ExtensionManager extensionManager;

    public void onPerformStatus(PerformItemContext context) {
        for (MemberPerformItemDO item : context.getItems()) {
            item.onFinishPerform(context);
            LambdaUpdateWrapper<MemberPerformItem> wrapper = new LambdaUpdateWrapper<>();
            wrapper.eq(MemberPerformItem::getUserId, context.getUserId())
                    .eq(MemberPerformItem::getItemToken, item.getItemToken())
                    .set(MemberPerformItem::getBatchCode, item.getBatchCode())
                    .set(MemberPerformItem::getStatus, item.getStatus().getCode())
                    .set(MemberPerformItem::getUtime, item.getUtime())
            ;

            extensionManager.getExtension(BizScene.of(context.getBizType()),
                    MemberPerformItemDomainExtension.class).onPerformSuccess(item, context, wrapper);
        }
    }
}