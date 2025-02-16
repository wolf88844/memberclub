/**
 * @(#)LockContext.java, 一月 23, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.common;

import com.memberclub.domain.common.BizTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author: 掘金五阳
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockContext {

    String lockScene;

    BizTypeEnum bizType;

    Long lockValue;

    long userId;

    String tradeId;

    LockMode lockMode;
}