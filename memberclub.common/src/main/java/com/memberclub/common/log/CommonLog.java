/**
 * @(#)CommonLog.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author 掘金五阳
 */
public class CommonLog {

    public static final Logger LOG = LoggerFactory.getLogger(CommonLog.class);


    public static void info(String format, Object... args) {
        LOG.info(format, args);
    }

    public static void warn(String format, Object... args) {
        LOG.warn(format, args);
    }

    public static void error(String format, Object... args) {
        LOG.error(format, args);
    }
}