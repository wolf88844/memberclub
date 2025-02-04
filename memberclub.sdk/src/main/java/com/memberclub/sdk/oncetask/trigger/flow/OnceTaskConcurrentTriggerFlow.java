/**
 * @(#)OnceTaskTriggerOnDataSourceFlow.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.oncetask.trigger.flow;

import com.memberclub.common.flow.SubFlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerContext;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerJobContext;
import lombok.SneakyThrows;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */
@Service
public class OnceTaskConcurrentTriggerFlow extends SubFlowNode<OnceTaskTriggerContext, OnceTaskTriggerJobContext> {


    private ExecutorService executorService =
            new ThreadPoolExecutor(10, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(100),
                    new CustomizableThreadFactory("once_task_trigger_thread_"));

    @Override
    @SneakyThrows
    public void process(OnceTaskTriggerContext context) {
        List<Callable<OnceTaskTriggerJobContext>> runnables = CollectionUtilEx.mapToList(context.getJobs(), (job) -> {
            return () -> {
                OnceTaskTriggerJobContext jobContext = new OnceTaskTriggerJobContext();
                jobContext.setContext(context);
                jobContext.setJob(job);
                getSubChain().execute(jobContext);
                return jobContext;
            };
        });

        List<Future<OnceTaskTriggerJobContext>> futures = executorService.invokeAll(runnables);

        for (Future<OnceTaskTriggerJobContext> future : futures) {
            try {
                OnceTaskTriggerJobContext jobContext = future.get();
                CommonLog.info("任务执行成功:{}", jobContext);
            } catch (Exception e) {
                CommonLog.error("任务执行异常", e);
            }
        }
    }
}