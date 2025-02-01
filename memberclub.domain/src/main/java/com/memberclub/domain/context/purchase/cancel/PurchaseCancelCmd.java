/**
 * @(#)PurchaseCancelCmd.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase.cancel;

import com.memberclub.domain.common.BizTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseCancelCmd {

    private long userId;

    private String tradeId;

    private BizTypeEnum bizType;
    
}