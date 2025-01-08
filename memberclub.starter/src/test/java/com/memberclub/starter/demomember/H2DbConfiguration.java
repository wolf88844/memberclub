/**
 * @(#)H2DbConfiguration.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.demomember;

import org.h2.engine.Mode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * author: 掘金五阳
 */
@Component
public class H2DbConfiguration {

    @PostConstruct
    private static void initEnv() {
        Mode mode = Mode.getInstance("MYSQL");
        //mode.convertInsertNullToZero = false;
    }

}