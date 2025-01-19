/**
 * @(#)TestStarter.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.nonmock;

import com.memberclub.starter.AppStarter;
import com.memberclub.infrastructure.assets.facade.MockAssetsFacadeSPI;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 掘金五阳
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppStarter.class)
public abstract class NonMockBaseTest {
    @SpyBean
    private MockAssetsFacadeSPI couponGrantFacade;

    /*@Test
    public void test() {

    }*/

}