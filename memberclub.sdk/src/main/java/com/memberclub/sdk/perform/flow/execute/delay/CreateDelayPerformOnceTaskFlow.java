/**
 * @(#)CreateDelayPerformOnceTaskFlow.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute.delay;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.context.perform.delay.DelayItemContext;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.infrastructure.mybatis.mappers.OnceTaskDao;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Service
public class CreateDelayPerformOnceTaskFlow extends FlowNode<DelayItemContext> {

    @Autowired
    private PerformDomainService performDomainService;

    @Autowired
    private OnceTaskDao onceTaskDao;

    @Override
    public void process(DelayItemContext context) {
        List<OnceTask> tasks = context.getTasks().stream()
                .map(onceTaskDO -> PerformConvertor.INSTANCE.toOnceTask(onceTaskDO))
                .collect(Collectors.toList());
        
        int count = performDomainService.createOnceTask(tasks);
        if (count < tasks.size()) {
            List<String> taskTokens = tasks.stream().map(OnceTask::getTaskToken).collect(Collectors.toList());
            List<OnceTask> taskFromDb = onceTaskDao.queryTasks(context.getPerformContext().getUserId(), taskTokens);
            if (taskFromDb.size() != count) {
                CommonLog.error("新增周期履约任务失败 dbCount:{}, expectCount:{}, dbtasks:{}", taskFromDb.size(), tasks.size(), taskFromDb);
                Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                        "task_create", false);
                ResultCode.PERIOD_PERFORM_TASK_CREATE_ERROR.throwException();
            } else {
                CommonLog.warn("幂等新增周期履约任务 count:{}, tasks:{}", count, tasks);
                Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                        "task_create", "duplicated");
            }
        } else {
            CommonLog.warn("新增周期履约任务成功 count:{}, tasks:{}", count, tasks);
            Monitor.PERFORM_EXECUTE.counter(context.getPerformContext().getBizType(),
                    "task_create", true);
        }
    }
}