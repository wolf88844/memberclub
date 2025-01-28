/**
 * @(#)SkuTest.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.infrastructure.mybatis.mappers.sku.MemberSkuDao;
import com.memberclub.sdk.sku.service.SkuDomainService;
import com.memberclub.starter.demomember.TestDemoMemberPurchase;
import com.memberclub.starter.mock.MockBaseTest;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
public class SkuTest extends MockBaseTest {

    @Autowired
    MemberSkuDao memberSkuDao;

    public SkuInfoDO buildDoubleRightsSku(int cycle) {
        return TestDemoMemberPurchase.buildDoubleRightsSku(cycle);
    }

    @Autowired
    private SkuDomainService skuDomainService;

    @Test
    public void testAddSku() {
        /*if (!ApplicationContextUtils.isUnitTest()) {
            CommonLog.info("当前 Profile:{} 不执行本单测",
                    JsonUtils.toJson(ApplicationContextUtils.getContext().getEnvironment().getActiveProfiles()));
            //return;
        }*/

        SkuInfoDO skuInfoDO = buildDoubleRightsSku(3);
        skuDomainService.createMemberSku(skuInfoDO);
    }
}