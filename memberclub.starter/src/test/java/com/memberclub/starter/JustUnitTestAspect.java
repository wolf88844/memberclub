/**
 * @(#)JustUnitTestAspect.java, 一月 18, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.memberclub.common.util.ApplicationContextUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;

/**
 * author: 掘金五阳
 */
@Service
public class JustUnitTestAspect {
    public static final Logger LOG = LoggerFactory.getLogger(JustUnitTestAspect.class);

    @Pointcut("@annotation(JustUnitTest) && execution(public * *(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        String className = joinPoint.getSignature().getDeclaringType().getSimpleName();
        if (ApplicationContextUtils.isUnitTest()) {
            LOG.info("当前环境可执行本单测 class:{}, method:{}", className, method.getName());
            return joinPoint.proceed();
        } else {
            LOG.warn("当前环境不可执行本单测 profiles:{}, class:{}, method:{}", ApplicationContextUtils.getContext().getEnvironment().getActiveProfiles(),
                    className, method.getName());
            return null;
        }
    }
}