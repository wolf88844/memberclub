/**
 * @(#)FeignConfiguration.java, 十二月 25, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.assets.facade;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * author: 掘金五阳
 */
@EnableFeignClients(basePackages = "com.memberclub")
@EnableEurekaClient
@Configuration
@ConditionalOnProperty(name = "memberclub.infrastructure.feign.enabled", havingValue = "true", matchIfMissing = true)
public class FeignConfiguration {

}