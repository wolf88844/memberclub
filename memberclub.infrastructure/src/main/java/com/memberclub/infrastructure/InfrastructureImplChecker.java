/**
 * @(#)InfrastructureImplChecker.java, 十二月 26, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure;

import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.infrastructure.dynamic_config.DynamicConfig;
import com.memberclub.infrastructure.id.IdGenerator;
import com.memberclub.infrastructure.lock.DistributeLock;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

import static org.springframework.util.Assert.notNull;

/**
 * author: 掘金五阳
 */
@Service
@Order(2)
public class InfrastructureImplChecker {

    @PostConstruct
    public void init() {
        ApplicationContext applicationContext = ApplicationContextUtils.getContext();
        notNull(applicationContext, "未获取到 ApplicationContext");
        check(() -> applicationContext.getBean(DynamicConfig.class),
                "DynamicConfig 未获取到实现类,请关注 配置项 memberclub.infrastructure.config,默认 apollo ");

        check(() -> applicationContext.getBean(DistributeLock.class),
                "DistributeLock 未获取到实现类,请关注 配置项 memberclub.infrastructure.lock,默认 redis ");

        check(() -> applicationContext.getBean(IdGenerator.class),
                "IdGenerator 未获取到实现类,请关注 配置项 memberclub.infrastructure.id,默认 redis ");

    }

    public void check(Runnable runnable, String msg) {
        try {
            runnable.run();
        } catch (NoSuchBeanDefinitionException e) {
            throw new RuntimeException(msg, e);
        }
    }
}