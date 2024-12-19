/**
 * @(#)MockCouponGrantFacade.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastruct.facade.impl;

import com.google.common.collect.Maps;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.infrastructure.facade.CouponGrantFacade;
import com.memberclub.infrastructure.facade.data.CouponDO;
import com.memberclub.infrastructure.facade.data.CouponGrantItemDO;
import com.memberclub.infrastructure.facade.data.CouponGrantRequestDO;
import com.memberclub.infrastructure.facade.data.CouponGrantResponseDO;
import org.apache.commons.lang3.RandomStringUtils;
import org.assertj.core.util.Lists;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: 掘金五阳
 */
@Service
public class MockCouponGrantFacade implements CouponGrantFacade {

    private AtomicLong couponIdGenerator = new AtomicLong(System.currentTimeMillis());


    @Override
    public CouponGrantResponseDO grant(CouponGrantRequestDO requestDO) {
        CouponGrantResponseDO responseDO = new CouponGrantResponseDO();
        responseDO.setCode(0);
        Map<String, List<CouponDO>> map = Maps.newHashMap();
        for (CouponGrantItemDO grantItem : requestDO.getGrantItems()) {
            List<CouponDO> coupons = Lists.newArrayList();
            String batchCode = RandomStringUtils.random(10);
            for (int assetCount = grantItem.getAssetCount(); assetCount > 0; assetCount--) {
                CouponDO coupon = new CouponDO();
                coupon.setStime(grantItem.getStime());
                coupon.setEtime(grantItem.getEtime());
                coupon.setCtime(TimeUtil.now());
                coupon.setBatchCode(batchCode);
                coupon.setCouponId(couponIdGenerator.incrementAndGet());
                coupon.setCouponType(1);
                coupon.setUserId(requestDO.getUserId());
                coupons.add(coupon);
            }
            map.put(grantItem.getItemToken(), coupons);
        }
        responseDO.setItemToken2CouponMap(map);
        return responseDO;
    }
}