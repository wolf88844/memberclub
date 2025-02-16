/**
 * @(#)DownstreamExampleApplication.java, 十二月 20, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.downstream.examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * author: 掘金五阳
 */
@EnableEurekaClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class, scanBasePackages = {"com.memberclub"})
public class DownstreamExampleApplication {
    public static void main(String[] args) {
        SpringApplication.run(DownstreamExampleApplication.class, args);
    }
}