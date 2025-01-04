/**
 * @(#)TestDemoMember.java, 十二月 21, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.mock;

import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.starter.AppStarter;
import com.memberclub.starter.impl.MockAssetsFacade;
import com.memberclub.starter.impl.MockSkuBizService;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.atomic.AtomicLong;

/**
 * author: 掘金五阳
 */

@ActiveProfiles("ut")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppStarter.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class MockBaseTest {
    @SpyBean()
    public MockAssetsFacade couponGrantFacade;

    @Autowired
    public MockSkuBizService mockSkuBizService;

    @Autowired
    public MemberOrderDao memberOrderDao;

    @Autowired
    public MemberSubOrderDao memberSubOrderDao;

    public AtomicLong userIdGenerator = new AtomicLong(RandomUtils.nextInt());

    public AtomicLong orderIdGenerator = new AtomicLong(System.currentTimeMillis());

    @Test
    public void test() {

    }

}