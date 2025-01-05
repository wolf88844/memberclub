/**
 * @(#)AftersaleApplyCmd.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.apply;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.dataobject.aftersale.ApplySkuInfoDO;
import lombok.Data;
import org.springframework.lang.Nullable;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class AftersaleApplyCmd {

    private BizTypeEnum bizType;

    private long userId;

    private OrderSystemTypeEnum orderSystemTypeEnum;

    private String orderId;

    private String tradeId;

    private AftersaleSourceEnum source;

    /**
     * 目前不支持指定商品退
     */
    @Nullable
    private List<ApplySkuInfoDO> applySkus;

    private String operator;

    private String reason;

    private String digests;

    private Integer digestVersion;
}