/**
 * @(#)RetryableAspect.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.RetryableContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */
@Aspect
@Component
public class RetryableAspect {

    @Autowired
    private RetryService retryService;

    @Pointcut("@annotation(Retryable) && execution(public * *(..))")
    public void pointcut() {
    }

    @Around(value = "pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        //无参方法不处理
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Object[] args = joinPoint.getArgs();

        //获取注解
        Retryable annotation = method.getAnnotation(Retryable.class);
        if (annotation != null && args != null && args.length > 0) {
            try {
                Object response = joinPoint.proceed();
                return response;
            } catch (Exception e) {
                Object param = joinPoint.getArgs()[0];

                int retryTimes = 0;
                if (param instanceof RetryableContext) {
                    RetryableContext retryableContext = ((RetryableContext) param);
                    retryableContext.setRetryTimes(retryableContext.getRetryTimes() + 1);
                    retryTimes = retryableContext.getRetryTimes();
                } else {
                    throw e;
                }

                long delayTime = annotation.initialDelaySeconds() * (int) Math.pow(1, retryTimes);
                delayTime = delayTime > annotation.maxDelaySeconds() ? annotation.maxDelaySeconds() : delayTime;

                RetryMessage message = new RetryMessage();
                message.setBeanName(toLowerCaseFirst(joinPoint.getSignature().getDeclaringType().getSimpleName()));
                message.setBeanClassName(joinPoint.getSignature().getDeclaringType().getName());
                message.setMethodName(((MethodSignature) joinPoint.getSignature()).getMethod().getName());
                message.setArgs(JsonUtils.toJson(param));
                message.setArgsClassName(param.getClass().getName());
                message.setRetryTimes(retryTimes);
                message.setExpectedTime(TimeUtil.now() + TimeUnit.SECONDS.toMillis(delayTime));

                retryService.addRetryMessage(message);
                return null;
            }
        } else {
            return joinPoint.proceed();
        }
    }


    public static String toLowerCaseFirst(String str) {
        if (str == null || str.isEmpty()) {
            return str;
        }
        char firstChar = str.charAt(0);
        char updatedFirstChar = Character.toLowerCase(firstChar);
        String remainder = str.substring(1);
        return updatedFirstChar + remainder;
    }
}