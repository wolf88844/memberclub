/**
 * @(#)RedissonRetryServiceImpl.java, 一月 18, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.retry;

import com.memberclub.common.retry.AbstractRetryService;
import com.memberclub.common.retry.RetryMessage;
import com.memberclub.common.util.TimeUtil;
import org.redisson.RedissonShutdownException;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.concurrent.CustomizableThreadFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */

@ConditionalOnProperty(name = "memberclub.infrastructure.retry", havingValue = "redisson", matchIfMissing = false)
@Service
public class RedissonRetryServiceImpl extends AbstractRetryService {

    public static final Logger LOG = LoggerFactory.getLogger(RedissonRetryServiceImpl.class);

    private ExecutorService executorService =
            new ThreadPoolExecutor(10, 10,
                    0L, TimeUnit.MILLISECONDS,
                    new ArrayBlockingQueue<>(100),
                    new CustomizableThreadFactory("retry_delay_queue_consumer_"));


    @Autowired
    private RedissonClient redissonClient;

    private static final String RETRY_DELAY_QUEUE = "retry_delay_queue";

    @Override
    public void addRetryMessage(RetryMessage message) {
        addDelayedTask(RETRY_DELAY_QUEUE, message,
                message.getExpectedTime() - TimeUtil.now(), TimeUnit.MILLISECONDS);
    }


    /**
     * 添加延迟任务
     *
     * @param delayedName 延迟名称
     * @param val         值
     * @param delayTime   延迟时间
     * @param timeUnit    时间单位
     */
    public void addDelayedTask(String delayedName, RetryMessage val, long delayTime, TimeUnit timeUnit) {
        final RDelayedQueue<RetryMessage> delayedQueue = getDelayedQueue(delayedName);
        delayedQueue.offer(val, delayTime, timeUnit);
    }

    /**
     * 获取阻塞deque
     *
     * @param queueName 队列名称
     * @return {@link RBlockingDeque}<{@link RetryMessage}>
     */
    public RBlockingDeque<RetryMessage> getBlockingDeque(String queueName) {
        return redissonClient.getBlockingDeque(queueName, JsonJacksonCodec.INSTANCE);
    }

    /**
     * 获取延迟队列
     *
     * @param queueName 队列名称
     * @return {@link RDelayedQueue}<{@link RetryMessage}>
     */
    private RDelayedQueue<RetryMessage> getDelayedQueue(String queueName) {
        return redissonClient.getDelayedQueue(getBlockingDeque(queueName));
    }

    /**
     * 获取延迟队列
     *
     * @param blockingDeque 阻塞deque
     * @return {@link org.redisson.api.RDelayedQueue}<{@link RetryMessage}>
     */
    private RDelayedQueue<RetryMessage> getDelayedQueue(RBlockingDeque<RetryMessage> blockingDeque) {
        return redissonClient.getDelayedQueue(blockingDeque);
    }

    @PostConstruct
    public void run() throws Exception {
        final Thread thread = new Thread(() -> {
            final RBlockingDeque<RetryMessage> blockingDeque = getBlockingDeque(RETRY_DELAY_QUEUE);
            while (true) {
                try {
                    //将到期的数据取出来，等待超时
                    final RetryMessage retryMessage = blockingDeque.poll(2, TimeUnit.MINUTES);
                    if (Objects.isNull(retryMessage)) {
                        continue;
                    }
                    LOG.info("DelayedTask task :[{}]", retryMessage);
                    executorService.submit(() -> consumeMessage(retryMessage));
                } catch (RedissonShutdownException e) {
                    LOG.warn("延迟任务终止轮训: DelayedTaskListener#delayedTaskHandle error delayedQueueName:[{}]", RETRY_DELAY_QUEUE);
                    break;
                } catch (Exception e) {
                    LOG.error("DelayedTaskListener#delayedTaskHandle error delayedQueueName:[{}]",
                            RETRY_DELAY_QUEUE, e);
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }
}