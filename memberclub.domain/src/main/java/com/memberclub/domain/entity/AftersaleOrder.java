/**
 * @(#)AftersaleOrder.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class AftersaleOrder {

    private Long id;

    private int bizType;

    private long userId;

    private int source;

    private String operator;

    private String tradeId;

    private String applySkuDetails;

    private String extra;

    private Integer actPayPriceFen;

    private Integer actRefundPriceFen;

    private Integer recommendRefundPriceFen;

    private int status;

    private int refundType;

    private long utime;

    private long ctime;

}