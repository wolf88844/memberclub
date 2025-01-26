/**
 * @(#)PreFinanceEvent.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.prefinance;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PreFinanceEvent {

    private int event;

    private long userId;

    private String bizOrderId;

    private int financeProductType;

    private String financeContractorId;

    private int periodCount;

    private int periodIndex;

    private long stime;

    private long etime;

    private List<PreFinanceEventDetail> details;
}