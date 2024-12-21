/**
 * @(#)CouponController.java, 十二月 20, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.downstream.examples.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.domain.facade.AssetDO;
import com.memberclub.domain.facade.GrantItemDO;
import com.memberclub.domain.facade.GrantRequestDO;
import com.memberclub.domain.facade.GrantResponseDO;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: 掘金五阳
 */
@RestController
public class CouponController {
    private AtomicLong couponIdGenerator = new AtomicLong(System.currentTimeMillis());

    @PostConstruct
    public void init() {
        System.out.println("ok");
    }

    @RequestMapping(method = RequestMethod.POST, value = "/items/grant")
    public GrantResponseDO grant(@RequestBody GrantRequestDO requestDO) {
        System.out.println("接受到请求");
        GrantResponseDO responseDO = new GrantResponseDO();
        responseDO.setCode(0);
        Map<String, List<AssetDO>> map = Maps.newHashMap();
        for (GrantItemDO grantItem : requestDO.getGrantItems()) {
            List<AssetDO> coupons = Lists.newArrayList();
            String batchCode = RandomStringUtils.random(10);
            for (int assetCount = grantItem.getAssetCount(); assetCount > 0; assetCount--) {
                AssetDO coupon = new AssetDO();
                coupon.setStime(grantItem.getStime());
                coupon.setEtime(grantItem.getEtime());
                coupon.setCtime(System.currentTimeMillis());
                coupon.setBatchCode(batchCode);
                coupon.setAssetId(couponIdGenerator.incrementAndGet());
                coupon.setAssetType(1);
                coupon.setUserId(requestDO.getUserId());
                coupons.add(coupon);
            }
            map.put(grantItem.getItemToken(), coupons);
        }
        responseDO.setItemToken2CouponMap(map);
        return responseDO;
    }
}