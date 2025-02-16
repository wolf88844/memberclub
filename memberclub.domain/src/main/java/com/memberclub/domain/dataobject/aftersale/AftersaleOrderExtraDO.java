/**
 * @(#)AftersaleOrderExtraDO.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class AftersaleOrderExtraDO {

    private List<ApplySkuInfoDO> applySkus;

    private String reason;

    private String orderRefundId;
}