/**
 * @(#)CouponGrantRequestDO.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.facade.data;

import com.memberclub.infrastructure.facade.data.CouponGrantItemDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class CouponGrantRequestDO {

    private long userId;

    private List<CouponGrantItemDO> grantItems;
}