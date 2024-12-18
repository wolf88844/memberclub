/**
 * @(#)CouponGrantFacade.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.facade;

import com.memberclub.infrastructure.facade.data.CouponGrantRequestDO;
import com.memberclub.infrastructure.facade.data.CouponGrantResponseDO;

/**
 * @author yuhaiqiang
 */
public interface CouponGrantFacade {

    public CouponGrantResponseDO grant(CouponGrantRequestDO requestDO);
}