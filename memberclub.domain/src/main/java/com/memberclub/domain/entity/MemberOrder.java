/**
 * @(#)MemberOrder.java, 十二月 14, 2024.
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
public class MemberOrder {

    private int bizType;

    private long userId;

    private int orderSystemType;

    private String orderId;

    private String userInfo;

    private String skuDetails;

    private String actPriceFen;

    private String originPriceFen;

    private int status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}