/**
 * @(#)FeignConfiguration.java, 十二月 25, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.facade;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * author: 掘金五阳
 */
@EnableFeignClients(basePackages = "com.memberclub")
@EnableEurekaClient
@Configuration
@Profile("!ut")
public class FeignConfiguration {

}