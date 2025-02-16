/**
 * @(#)PurchaseController.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.controller;

import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.sdk.purchase.service.biz.PurchaseBizService;
import com.memberclub.starter.controller.convertor.PurchaseConvertor;
import com.memberclub.starter.controller.vo.PurchaseSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * author: 掘金五阳
 */
@RestController("/memberclub/purchase")
public class PurchaseController {

    @Autowired
    private PurchaseBizService purchaseBizService;

    @PostMapping("/submit")
    public void submit(@RequestBody PurchaseSubmitVO param) {
        try {
            PurchaseSubmitCmd cmd = PurchaseConvertor.toSubmitCmd(param);
            purchaseBizService.submit(cmd);
        } catch (Exception e) {

        }
    }
}