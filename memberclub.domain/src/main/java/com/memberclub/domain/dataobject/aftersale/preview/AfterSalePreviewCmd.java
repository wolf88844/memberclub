/**
 * @(#)AfterSalePreviewCmd.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale.preview;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.dataobject.aftersale.AftersaleSourceEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class AfterSalePreviewCmd {

    private BizTypeEnum bizType;

    private long userId;

    private OrderSystemTypeEnum orderSystemTypeEnum;

    private String orderId;

    private String tradeId;

    private AftersaleSourceEnum source;

    private String operator;

    private Integer digestVersion;
}