/**
 * @(#)AppStarter.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 掘金五阳
 */

@SpringBootApplication(scanBasePackages = {"com.memberclub"})
public class AppStarter {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(AppStarter.class);

        application.run(args);
    }
}