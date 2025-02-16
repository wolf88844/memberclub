/**
 * @(#)RefundOrderRequestDTO.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.order.facade;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class RefundOrderRequestDTO {

    private long userId;

    private String orderId;

    private int refundPriceFen;

    //1 全部退,2 部分退
    private int refundType;
}