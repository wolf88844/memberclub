/**
 * @(#)MapperConfiguration.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author 掘金五阳
 */
@MapperScan(basePackages = {"com.memberclub.infrastructure.mybatis.mappers"})
@Configuration
public class MapperConfiguration {

}