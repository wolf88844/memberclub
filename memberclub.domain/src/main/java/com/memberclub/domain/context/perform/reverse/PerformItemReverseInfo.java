/**
 * @(#)PerformItemReverseInfo.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.reverse;

import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PerformItemReverseInfo {

    private String itemToken;

    private String batchCode;
    
    private int rightType;

    private long skuId;

    private RefundTypeEnum refundType;
}