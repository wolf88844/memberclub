/**
 * @(#)RefundOrderResponseDTO.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.order.facade;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class RefundOrderResponseDTO {

    private boolean success;

    private int errorCode;

    private String errorMsg;

    private String orderRefundId;
}