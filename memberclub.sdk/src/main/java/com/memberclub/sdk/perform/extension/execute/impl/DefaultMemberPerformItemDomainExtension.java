/**
 * @(#)DefaultPerformItemDomainExtension.java, 一月 11, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.execute.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.perform.extension.execute.MemberPerformItemDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认 MemberPerformItem 数据库层扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultMemberPerformItemDomainExtension implements MemberPerformItemDomainExtension {

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Override
    public void onPerformSuccess(MemberPerformItemDO item, PerformItemContext context, LambdaUpdateWrapper<MemberPerformItem> wrapper) {
        int cnt = memberPerformItemDao.update(null, wrapper);
        if (cnt <= 0) {
            MemberPerformItem itemFromDb = memberPerformItemDao.queryByItemToken(context.getUserId(),
                    item.getItemToken());
            if (!PerformItemStatusEnum.hasPerformed(itemFromDb.getStatus())) {
                CommonLog.error("更新 item 失败 itemToken:{}", item.getItemToken());
                throw ResultCode.INTERNAL_ERROR.newException("更新 member_perform_item失败");
            }
        }
        CommonLog.info("成功更新 item 到履约完成itemToken:{}, batchCode:{}", item.getItemToken(), item.getBatchCode());
    }
}