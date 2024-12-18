/**
 * @(#)PerformService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service;

import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.sdk.extension.perform.BuildPerformContextExtension;
import com.memberclub.sdk.extension.perform.PreBuildPerformContextExtension;
import com.memberclub.sdk.extension.perform.execute.PerformExecuteExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PerformService {

    @Autowired
    private ExtensionManger extensionManger;

    @Retryable
    @UserLog(domain = LogDomainEnum.PERFORM)
    public void perform(PerformCmd cmd) {
        String preBuildScene = extensionManger.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildPreBuildPerformContextScene(cmd);

        PreBuildPerformContextExtension preBuildPerformContextExtension =
                extensionManger.getExtension(BizScene.of(cmd.getBizType().toBizType(), preBuildScene),
                        PreBuildPerformContextExtension.class);

        PerformContext context = preBuildPerformContextExtension.preBuild(cmd);

        if (context.isSkipPerform()) {
            // TODO: 2024/12/15  返回当前结果
            return;
        }

        String buildScene = extensionManger.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildBuildPerformContextScene(cmd);

        BuildPerformContextExtension buildPerformContextExtension =
                extensionManger.getExtension(BizScene.of(cmd.getBizType().toBizType(), buildScene),
                        BuildPerformContextExtension.class);
        buildPerformContextExtension.build(context);

        //execute Context

        String executeScene = extensionManger.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildPerformContextExecuteScene(context);
        PerformExecuteExtension performExecuteExtension = extensionManger.
                getExtension(BizScene.of(cmd.getBizType().toBizType(), executeScene), PerformExecuteExtension.class);
        performExecuteExtension.execute(context);

        //todo 处理 lockValue 重试
    }
}