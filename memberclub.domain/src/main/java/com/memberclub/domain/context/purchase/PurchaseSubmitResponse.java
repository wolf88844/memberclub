/**
 * @(#)PurchaseSubmitResponse.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase;

import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseSubmitResponse {

    private boolean success;

    private int errorCode;

    private Long lockValue;

    private String msg;

    private MemberOrderDO memberOrderDO;
}