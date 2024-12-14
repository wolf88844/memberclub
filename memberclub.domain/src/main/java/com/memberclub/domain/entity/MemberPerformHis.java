/**
 * @(#)MemberPerformHisPO.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity;

import lombok.Data;

/**
 * @author yuhaiqiang
 */
@Data
public class MemberPerformHis {

    private int bizType;

    private long userId;

    private int orderSystemType;

    private String orderId;

    private String tradeId;

    private long skuId;

    private String extra;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}