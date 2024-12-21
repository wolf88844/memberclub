/**
 * @(#)EureKaApplication.java, 十二月 20, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * author: 掘金五阳
 */
@EnableDiscoveryClient
@EnableEurekaServer
@SpringBootApplication
public class EureKaApplication {
    public static void main(String[] args) {
        SpringApplication.run(EureKaApplication.class, args);
    }
}