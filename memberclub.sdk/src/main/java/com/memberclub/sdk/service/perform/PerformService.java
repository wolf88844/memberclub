/**
 * @(#)PerformService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.perform;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformResp;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.extension.perform.build.BuildPerformContextExtension;
import com.memberclub.sdk.extension.perform.build.PreBuildPerformContextExtension;
import com.memberclub.sdk.extension.perform.execute.PerformExecuteExtension;
import com.memberclub.sdk.extension.perform.period.PeriodPerformExecuteExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PerformService {

    @Autowired
    private ExtensionManager extensionManager;


    @UserLog(domain = LogDomainEnum.PERFORM)
    public PerformResp periodPerform(OnceTaskDO task) {
        PeriodPerformContext context = PerformConvertor.INSTANCE.toPeriodPerformContextForTask(task);
        PeriodPerformExecuteExtension extension =
                extensionManager.getExtension(BizScene.of(context.getBizType()), PeriodPerformExecuteExtension.class);

        extension.buildContext(task, context);

        extension.periodPerform(context);

        return null;
    }


    @Retryable
    @UserLog(domain = LogDomainEnum.PERFORM)
    public PerformResp perform(PerformCmd cmd) {
        PerformResp resp = new PerformResp();
        try {
            String preBuildScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                    .buildPreBuildPerformContextScene(cmd);

            PerformContext context = extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType(), preBuildScene),
                    PreBuildPerformContextExtension.class).preBuild(cmd);

            if (context.isSkipPerform()) {
                if (MemberOrderStatusEnum.isPerformed(context.getMemberOrder().getStatus())) {
                    resp.setSuccess(true);
                    resp.setNeedRetry(false);
                } else {
                    resp.setSuccess(true);
                    resp.setNeedRetry(true);
                }
                Monitor.PERFORM.counter(cmd.getBizType(),
                        "retryTimes", cmd.getRetryTimes(), "skip", true, "result", resp.isSuccess());

                return resp;
            }

            String buildScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                    .buildBuildPerformContextScene(context);


            extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType(), buildScene),
                    BuildPerformContextExtension.class).build(context);

            //execute Context
            String executeScene = extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()))
                    .buildPerformContextExecuteScene(context);
            extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType(), executeScene),
                    PerformExecuteExtension.class).execute(context);

            resp.setSuccess(true);
            resp.setNeedRetry(false);

            Monitor.PERFORM.counter(cmd.getBizType(),
                    "retryTimes", cmd.getRetryTimes(), "skip", false, "result", resp.isSuccess());
            CommonLog.error("履约流程成功:{}", cmd);
        } catch (Exception e) {
            CommonLog.error("内部履约流程异常,需要重试:{}", cmd, e);
            resp.setSuccess(false);
            resp.setNeedRetry(true);

            Monitor.PERFORM.counter(cmd.getBizType(),
                    "retryTimes", cmd.getRetryTimes(), "skip", false, "result", "exception");
        }

        //todo 处理 失败 重试,需要由外层注解处理!
        return resp;
    }
}