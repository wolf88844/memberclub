/**
 * @(#)CouponGrantResponseDO.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.facade.data;

import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class CouponGrantResponseDO {

    private int code;

    private Map<String, List<CouponDO>> itemToken2CouponMap;

    public boolean isSuccess() {
        return code == 0;
    }
}