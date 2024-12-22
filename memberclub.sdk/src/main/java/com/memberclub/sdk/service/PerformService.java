/**
 * @(#)PerformService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformResp;
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
    private ExtensionManager extensionManager;

    @Retryable
    @UserLog(domain = LogDomainEnum.PERFORM)
    public PerformResp perform(PerformCmd cmd) {
        PerformResp resp = new PerformResp();

        String preBuildScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildPreBuildPerformContextScene(cmd);

        PreBuildPerformContextExtension preBuildPerformContextExtension =
                extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType(), preBuildScene),
                        PreBuildPerformContextExtension.class);

        PerformContext context = preBuildPerformContextExtension.preBuild(cmd);

        if (context.isSkipPerform()) {
            if (MemberOrderStatusEnum.hasPerformed(context.getMemberOrder().getStatus())) {
                resp.setSuccess(true);
                resp.setNeedRetry(false);
            } else {
                resp.setSuccess(true);
                resp.setNeedRetry(true);
            }

            return resp;
        }

        String buildScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildBuildPerformContextScene(context);

        BuildPerformContextExtension buildPerformContextExtension =
                extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType(), buildScene),
                        BuildPerformContextExtension.class);
        buildPerformContextExtension.build(context);

        //execute Context

        String executeScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                .buildPerformContextExecuteScene(context);
        PerformExecuteExtension performExecuteExtension = extensionManager.
                getExtension(BizScene.of(cmd.getBizType().toBizType(), executeScene), PerformExecuteExtension.class);
        performExecuteExtension.execute(context);

        if (context.isSuccess()) {
            resp.setSuccess(true);
            resp.setNeedRetry(false);
        } else {
            resp.setSuccess(false);
            resp.setNeedRetry(true);
        }


        //todo 处理 失败 重试,需要由外层注解处理!
        return resp;
    }
}