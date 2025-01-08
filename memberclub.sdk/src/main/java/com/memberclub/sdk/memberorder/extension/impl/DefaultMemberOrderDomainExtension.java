/**
 * @(#)DefaultMemberOrderDomainExtension.java, 一月 08, 2025.
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
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.sdk.memberorder.extension.MemberOrderDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认 MemberOrder数据库层扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultMemberOrderDomainExtension implements MemberOrderDomainExtension {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Override
    public void onSubmitSuccess(MemberOrderDO memberOrderDO, LambdaUpdateWrapper<MemberOrder> wrapper) {
        int cnt = memberOrderDao.update(null, wrapper);
        return;
    }

}