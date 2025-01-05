/**
 * @(#)SubmitResponseDTO.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.order.context;

import com.memberclub.domain.dataobject.purchase.facade.SkuBuyResultDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SubmitOrderResponseDTO {

    private boolean success;

    private int errorCode;

    private String msg;

    private String orderId;

    private Integer actPriceFen;

    private List<SkuBuyResultDO> skuBuyResults;
}