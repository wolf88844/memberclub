/**
 * @(#)DefaultMemberSubOrderDomainExtension.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.extension.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.memberorder.extension.MemberSubOrderDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认 MemberSubOrder数据库层扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultMemberSubOrderDomainExtension implements MemberSubOrderDomainExtension {

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    @Override
    public void onSubmitSuccess(MemberSubOrderDO memberSubOrderDO, LambdaUpdateWrapper<MemberSubOrder> wrapper) {
        memberSubOrderDao.update(null, wrapper);
    }

    @Override
    public void onPerformSuccess(PerformContext performContext,
                                 SubOrderPerformContext subOrderPerformContext,
                                 MemberSubOrderDO memberSubOrderDO,
                                 LambdaUpdateWrapper<MemberSubOrder> wrapper) {
        memberSubOrderDao.update(null, wrapper);
    }
}