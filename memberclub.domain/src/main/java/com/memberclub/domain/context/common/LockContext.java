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
 * LockContext 是一个用于描述锁定上下文的类，主要用于在业务处理过程中确保数据的一致性和防止并发冲突。
 * 该类包含锁定场景、业务类型、锁定值、用户ID、交易ID和锁定模式等信息。
 *
 * @author 掘金五阳
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LockContext {

    /**
     * 锁定场景，描述当前锁定的具体业务场景或用途。
     */
    String lockScene;

    /**
     * 业务类型，枚举类型 {@link BizTypeEnum}，表示当前锁定所涉及的业务类型。
     */
    BizTypeEnum bizType;

    /**
     * 锁定值，通常是一个与锁定相关的具体数值，如订单号或其他唯一标识符。
     */
    Long lockValue;

    /**
     * 用户ID，表示触发锁定操作的用户标识。
     */
    long userId;

    /**
     * 交易ID，表示与锁定相关的交易标识，通常用于追踪特定交易的操作。
     */
    String tradeId;

    /**
     * 锁定模式，表示当前锁定的操作模式，例如排他锁、共享锁等。
     */
    LockMode lockMode;
}
