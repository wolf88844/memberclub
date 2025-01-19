/**
 * @(#)OnceTaskDomainService.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.periodperform.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.entity.trade.OnceTask;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.infrastructure.mybatis.mappers.trade.OnceTaskDao;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.oncetask.periodperform.extension.PeriodPerformTaskDomainExtension;
import com.memberclub.sdk.perform.service.domain.PerformDataObjectBuildFactory;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum.FAIL;
import static com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum.INIT;
import static com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum.PROCESSING;

/**
 * author: 掘金五阳
 */
@DS("tradeDataSource")
@Service
public class PeriodPerformTaskDomainService {
    @Autowired
    private OnceTaskDao onceTaskDao;

    @Autowired
    private PerformDataObjectBuildFactory performDataObjectBuildFactory;

    @Autowired
    private ExtensionManager extensionManager;

    @Transactional(rollbackFor = Exception.class)
    public void onCreatePeriodPerformTask(DelayItemContext context) {
        List<OnceTask> tasks = context.getTasks().stream()
                .map(onceTaskDO -> PerformConvertor.INSTANCE.toOnceTask(onceTaskDO))
                .collect(Collectors.toList());

        int count = onceTaskDao.insertIgnoreBatch(tasks);
        if (count < tasks.size()) {
            List<String> taskTokens = tasks.stream().map(OnceTask::getTaskToken).collect(Collectors.toList());
            List<OnceTask> taskFromDb = onceTaskDao.queryTasks(context.getPerformContext().getUserId(), taskTokens);
            if (taskFromDb.size() != count) {
                CommonLog.error("新增周期履约任务失败 dbCount:{}, expectCount:{}, dbtasks:{}", taskFromDb.size(), tasks.size(), taskFromDb);
                Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                        "task_create", false);
                throw ResultCode.PERIOD_PERFORM_TASK_CREATE_ERROR.newException();
            } else {
                CommonLog.warn("幂等新增周期履约任务 count:{}, tasks:{}", count, tasks);
                Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                        "task_create", "duplicated");
            }
            return;
        }
        CommonLog.warn("新增周期履约任务成功 count:{}, tasks:{}", count, tasks);
        Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                "task_create", true);
    }

    public void buildActivePeriodTasks(ReversePerformContext context,
                                       Map<String, SubOrderReversePerformContext> subTradeId2SubOrderReversePerformContext) {
        for (Map.Entry<String, SubOrderReversePerformContext> entry : subTradeId2SubOrderReversePerformContext.entrySet()) {
            SubOrderReversePerformContext subOrderReversePerformContext = entry.getValue();

            LambdaQueryWrapper<OnceTask> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(OnceTask::getUserId, context.getUserId())
                    .eq(OnceTask::getTaskGroupId, String.valueOf(subOrderReversePerformContext.getSubTradeId()))
                    .in(OnceTask::getStatus, Lists.newArrayList(FAIL.getCode(), PROCESSING.getCode(), INIT.getCode()));

            List<OnceTask> tasks = onceTaskDao.selectList(queryWrapper);
            List<OnceTaskDO> taskDOList = tasks.stream().map(performDataObjectBuildFactory::toOnceTaskDO)
                    .collect(Collectors.toList());

            List<String> activeTaskTokens =
                    taskDOList.stream().map(OnceTaskDO::getTaskToken).collect(Collectors.toList());

            subOrderReversePerformContext.setActiveTasks(taskDOList);
            subOrderReversePerformContext.setActiveTaskTokens(activeTaskTokens);
        }
    }

    public void cancelActivatPeriodTasks(ReversePerformContext context,
                                         Map<String, SubOrderReversePerformContext> subTradeId2SubOrderReversePerformContext) {
        for (Map.Entry<String, SubOrderReversePerformContext> entry : subTradeId2SubOrderReversePerformContext.entrySet()) {
            if (CollectionUtils.isNotEmpty(entry.getValue().getActiveTasks())) {
                for (OnceTaskDO activeTask : entry.getValue().getActiveTasks()) {
                    activeTask.onCancel(context, entry.getValue());
                }

                LambdaUpdateWrapper<OnceTask> wrapper = new LambdaUpdateWrapper<>();
                wrapper.eq(OnceTask::getUserId, context.getUserId())
                        .in(OnceTask::getTaskToken, entry.getValue().getActiveTaskTokens())
                        .set(OnceTask::getStatus, OnceTaskStatusEnum.CANCEL.getCode())
                        .set(OnceTask::getUtime, TimeUtil.now())
                ;

                extensionManager.getExtension(BizScene.of(context.getBizType()),
                        PeriodPerformTaskDomainExtension.class).onCancel(context, entry.getValue(), wrapper);

            }
        }
    }


}