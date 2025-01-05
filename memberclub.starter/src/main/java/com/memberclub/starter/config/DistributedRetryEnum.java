/**
 * @(#)DistributedRetryEnum.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.config;

/**
 * @author yuhaiqiang
 * 添加枚举后,需要编译一下,IDE 才会在 yml 中推荐对应的值
 */
public enum DistributedRetryEnum {

    local,
    rabbitmq,
    ;
}
