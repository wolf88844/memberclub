/**
 * @(#)CouponGrantFacade.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.facade;

import com.memberclub.domain.facade.GrantRequestDO;
import com.memberclub.domain.facade.GrantResponseDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author yuhaiqiang
 */
@FeignClient("downstream-examples")
public interface CouponGrantFacade {

    @RequestMapping(method = RequestMethod.POST, value = "/items/grant")
    public GrantResponseDO grant(@RequestBody GrantRequestDO requestDO);
}