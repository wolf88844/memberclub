/**
 * @(#)ApplicationContextUtils.java, 十二月 26, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.util;

import com.memberclub.common.log.CommonLog;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * author: 掘金五阳
 * 第一个启动
 */
@Configuration
@Order(1)
public class ApplicationContextUtils implements ApplicationContextAware {


    private static ApplicationContext context;


    public static ApplicationContext getContext() {
        return context;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CommonLog.warn("ApplicationContextUtils 可正常获取 applcationContext");
        context = applicationContext;
    }

    public static boolean isTest() {
        String[] profiles = ApplicationContextUtils.getContext().getEnvironment().getActiveProfiles();
        if (profiles != null) {
            for (String profile : profiles) {
                if (!"online".equals(profile)) {
                    return true;
                }
            }
        }
        return false;
    }
}