/**
 * @(#)CouponGrantItemDO.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.facade;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class GrantItemDO {

    private String itemToken;

    private String channelKey;

    private int assetCount;

    private long stime;

    private long etime;
}