/**
 * @(#)PurchaseCancelContext.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase.cancel;

import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseCancelContext {


    private PurchaseCancelCmd cmd;

    private MemberOrderDO memberOrder;

    private Long lockValue;
}