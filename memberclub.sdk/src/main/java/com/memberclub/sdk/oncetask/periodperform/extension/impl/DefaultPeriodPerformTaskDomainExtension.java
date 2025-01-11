/**
 * @(#)DefaultOnceTaskDomainExtension.java, 一月 11, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.periodperform.extension.impl;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.infrastructure.mybatis.mappers.OnceTaskDao;
import com.memberclub.sdk.oncetask.periodperform.extension.PeriodPerformTaskDomainExtension;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认周期履约任务扩展点实现", bizScenes = {
        @Route(bizType = BizTypeEnum.DEMO_MEMBER)
})
public class DefaultPeriodPerformTaskDomainExtension implements PeriodPerformTaskDomainExtension {

    @Autowired
    private OnceTaskDao onceTaskDao;

    @Override
    public void onCancel(ReversePerformContext reversePerformContext,
                         SubOrderReversePerformContext context,
                         LambdaUpdateWrapper<OnceTask> wrapper) {
        int cnt = onceTaskDao.update(null, wrapper);

        CommonLog.info("取消生效中的周期履约任务 taskTokens:{}, cnt:{}", context.getActiveTaskTokens(), cnt);
    }
}