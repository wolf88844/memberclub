/**
 * @(#)ManageController.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.controller;

import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.sdk.sku.service.SkuDomainService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Profile({"ut", "test"})
@RestController("/manage")
@Api(value = "管理接口", tags = {"商品列表"})
public class ManageController {

    @Autowired
    private SkuDomainService skuDomainService;

    @ApiOperation("查询商品列表")
    @PostMapping("/sku/list")
    @ResponseBody
    public List<SkuInfoDO> listSkus() {
        return skuDomainService.queryAllSkus();
    }
}