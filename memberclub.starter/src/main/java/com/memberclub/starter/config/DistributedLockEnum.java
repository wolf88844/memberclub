/**
 * @(#)DistributedLockEnum.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.config;

/**
 * @author wuyang
 * 添加枚举后,需要编译一下,IDE 才会在 yml 中推荐对应的值
 */
public enum DistributedLockEnum {

    local,
    redisson,
    redis,
}
