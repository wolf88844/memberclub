/**
 * @(#)LocalRetryService.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.retry.impl;

import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.RetryMessage;
import com.memberclub.common.retry.RetryService;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.common.util.JsonUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import java.lang.reflect.Method;
import java.util.concurrent.DelayQueue;

/**
 * author: 掘金五阳
 */
@ConditionalOnProperty(name = "memberclub.infrastructure.retry", havingValue = "none", matchIfMissing = true)
@Service
public class LocalRetryService implements RetryService {


    private DelayQueue<RetryMessage> delayQueue = new DelayQueue<RetryMessage>();

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
                        consumeMessage(message);
                    }
                } catch (Exception e) {

                }
            }
        }).start();
    }

    @Override
    public void consumeMessage(RetryMessage msg) {
        try {
            CommonLog.info("开始消费重试消息 {}", msg);
            Object bean = ApplicationContextUtils.getContext().getBean(msg.getBeanName());
            Class<?> clazz = Class.forName(msg.getBeanClassName());
            Class<?> argsClass = Class.forName(msg.getArgsClassName());
            Method m = ReflectionUtils.findMethod(clazz, msg.getMethodName(), argsClass);


            Object p = JsonUtils.fromJson(msg.getArgs(), argsClass);

            ReflectionUtils.invokeMethod(m, bean, p);
        } catch (Exception e1) {
            CommonLog.error("重试消费异常:{}", msg, e1);
        }
    }
}