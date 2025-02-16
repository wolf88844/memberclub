/**
 * @(#)CouponGrantFacade.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.assets.facade;

import com.memberclub.domain.facade.AssetFetchRequestDO;
import com.memberclub.domain.facade.AssetFetchResponseDO;
import com.memberclub.domain.facade.AssetReverseRequestDO;
import com.memberclub.domain.facade.AssetReverseResponseDO;
import com.memberclub.domain.facade.GrantRequestDO;
import com.memberclub.domain.facade.GrantResponseDO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author wuyang
 */
@FeignClient("downstream-examples")
public interface AssetsFacadeSPI {

    @RequestMapping(method = RequestMethod.POST, value = "/items/grant")
    public GrantResponseDO grant(@RequestBody GrantRequestDO requestDO);

    @RequestMapping(method = RequestMethod.POST, value = "/items/fetch")
    public AssetFetchResponseDO fetch(@RequestBody AssetFetchRequestDO request);

    @RequestMapping(method = RequestMethod.POST, value = "/items/reverse")
    public AssetReverseResponseDO reverse(@RequestBody AssetReverseRequestDO request);
}