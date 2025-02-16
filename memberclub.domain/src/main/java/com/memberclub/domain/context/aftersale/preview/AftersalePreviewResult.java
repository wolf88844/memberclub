/**
 * @(#)AftersalePreviewResult.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.preview;

import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class AftersalePreviewResult {

    private boolean aftersaleEnabled;

    private RefundTypeEnum refundType;


}