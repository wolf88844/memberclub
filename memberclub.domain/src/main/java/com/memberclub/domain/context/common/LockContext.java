/**
 * @(#)LockContext.java, 一月 23, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.common;

import com.memberclub.domain.common.BizTypeEnum;
import lombok.Builder;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Builder
@Data
public class LockContext {

    String lockScene;

    BizTypeEnum bizType;

    Long lockValue;

    long userId;

    String tradeId;

    LockMode lockMode;

    boolean unlockOnPeriodPerformFail;

    boolean unlockOnAfterSaleFail;
}