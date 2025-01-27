/**
 * @(#)OnceTaskTriggerDomainService.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.oncetask.execute.OnceTaskExecuteContext;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerJobContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.entity.trade.OnceTask;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.CommonConvertor;
import com.memberclub.infrastructure.mybatis.mappers.trade.OnceTaskDao;
import com.memberclub.sdk.oncetask.trigger.extension.OnceTaskTriggerExtension;
import com.memberclub.sdk.perform.service.domain.PerformDataObjectBuildFactory;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Consumer;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskDomainService {

    public static final Logger LOG = LoggerFactory.getLogger(OnceTaskDomainService.class);

    @Autowired
    private ExtensionManager extensionManager;
    @Autowired
    private OnceTaskDao onceTaskDao;
    @Autowired
    private PerformDataObjectBuildFactory performDataObjectBuildFactory;

    public void scanTasks(OnceTaskTriggerJobContext context, Consumer<List<OnceTask>> consumer) {
        Long minId = 0L;
        int page = 1;
        List<OnceTask> tasks = null;
        do {
            tasks = onceTaskDao.scanTasks(context.getContext().getBizType().getCode(),
                     context.getContext().getUserIds(),
                    context.getContext().getMinTriggerStime(), context.getContext().getMaxTriggerStime(),
                    minId, page);
            if (CollectionUtils.isNotEmpty(tasks)) {
                minId = tasks.get(tasks.size() - 1).getId();
            }

            consumer.accept(tasks);
        } while (CollectionUtils.isNotEmpty(tasks));
    }

    public void execute(OnceTaskTriggerJobContext context, List<OnceTask> tasks) {
        for (OnceTask task : tasks) {
            OnceTaskDO taskDO = performDataObjectBuildFactory.toOnceTaskDO(task);
            OnceTaskExecuteContext executeContext = CommonConvertor.INSTANCE.toOnceTaskExecuteContext(context.getContext());
            executeContext.setOnceTask(taskDO);
            try {
                extensionManager.getExtension(BizScene.of(context.getContext().getBizType()),
                        OnceTaskTriggerExtension.class).execute(executeContext);
                LOG.info("执行任务成功 bizType:{}, taskType:{}, taskToken:{}, taskGroupId:{}",
                        taskDO.getBizType(), taskDO.getTaskType(), taskDO.getTaskToken(), task.getTaskGroupId());
            } catch (Throwable e) {
                LOG.error("执行 OnceTask任务失败 task:{}", taskDO, e);
            }

        }
    }

    public void onStartExecute(OnceTaskExecuteContext context, OnceTaskDO task) {
        task.onStartExecute(context);
        LambdaUpdateWrapper<OnceTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OnceTask::getUserId, task.getUserId());
        wrapper.eq(OnceTask::getTaskToken, task.getTaskToken());
        wrapper.ne(OnceTask::getStatus, OnceTaskStatusEnum.SUCCESS.getCode());
        wrapper.set(OnceTask::getStatus, task.getStatus().getCode());

        int cnt = onceTaskDao.update(null, wrapper);
        if (cnt < 1) {
            throw ResultCode.DATA_UPDATE_ERROR.newException("执行任务时,更新为执行中异常");
        }
    }

    public void onExecuteSuccess(OnceTaskExecuteContext context, OnceTaskDO task) {
        task.onExecuteSuccess(context);
        LambdaUpdateWrapper<OnceTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OnceTask::getUserId, task.getUserId());
        wrapper.eq(OnceTask::getTaskToken, task.getTaskToken());
        wrapper.set(OnceTask::getStatus, task.getStatus().getCode());

        int cnt = onceTaskDao.update(null, wrapper);
        if (cnt < 1) {
            throw ResultCode.DATA_UPDATE_ERROR.newException("执行任务时,更新为执行中异常");
        }
    }

    public void onExecuteFail(OnceTaskExecuteContext context, OnceTaskDO task) {
        task.onExecuteFail(context);
        LambdaUpdateWrapper<OnceTask> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(OnceTask::getUserId, task.getUserId());
        wrapper.eq(OnceTask::getTaskToken, task.getTaskToken());
        wrapper.ne(OnceTask::getStatus, OnceTaskStatusEnum.SUCCESS.getCode());
        wrapper.set(OnceTask::getStatus, task.getStatus().getCode());

        int cnt = onceTaskDao.update(null, wrapper);
        if (cnt < 1) {
            throw ResultCode.DATA_UPDATE_ERROR.newException("执行任务时,更新为执行中异常");
        }
    }
}