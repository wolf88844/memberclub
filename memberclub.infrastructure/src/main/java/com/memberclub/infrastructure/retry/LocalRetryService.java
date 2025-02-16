/**
 * @(#)LocalRetryService.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.retry;

import com.memberclub.common.retry.AbstractRetryService;
import com.memberclub.common.retry.RetryMessage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.retry", havingValue = "local", matchIfMissing = false)
@Service
public class LocalRetryService extends AbstractRetryService {


    private DelayQueue<RetryMessage> delayQueue = new DelayQueue<RetryMessage>();

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    @Override
    public void addRetryMessage(RetryMessage message) {
        delayQueue.offer(message);
    }

    @PostConstruct
    public void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    while (true) {
                        RetryMessage message = delayQueue.take();

                        executorService.submit(() -> consumeMessage(message));
                    }
                } catch (Exception e) {

                }
            }
        }).start();
    }
}