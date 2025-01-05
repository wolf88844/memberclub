/**
 * @(#)CommonOrderFacade.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.order.facade;

import com.memberclub.infrastructure.order.context.SubmitOrderRequestDTO;
import com.memberclub.infrastructure.order.context.SubmitOrderResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * author: 掘金五阳
 */
@FeignClient("downstream-examples")
public interface CommonOrderFacadeSPI {

    @RequestMapping(method = RequestMethod.POST, value = "/order/submit")
    public SubmitOrderResponseDTO submit(SubmitOrderRequestDTO request);
}