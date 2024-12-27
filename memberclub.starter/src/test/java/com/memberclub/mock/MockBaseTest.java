/**
 * @(#)TestDemoMember.java, 十二月 21, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.mock;

import com.memberclub.starter.AppStarter;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * author: 掘金五阳
 */

@ActiveProfiles("ut")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppStarter.class)
public class MockBaseTest {


}