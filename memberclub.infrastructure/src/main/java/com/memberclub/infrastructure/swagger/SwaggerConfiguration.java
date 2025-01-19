/**
 * @(#)SwaggerConfiguration.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.swagger;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * author: 掘金五阳
 */

@Profile({"ut", "test"})
@EnableSwagger2
@Configuration
public class SwaggerConfiguration {

    
}

