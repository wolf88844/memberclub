/**
 * @(#)TestExtension.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.memberclub.common.BizScene;
import com.memberclub.common.biz.BizTypeEnum;
import com.memberclub.common.biz.SceneEnum;
import com.memberclub.common.exception.MemberException;
import com.memberclub.common.extension.ExtensionManger;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.sdk.extension.PerformExtension;
import com.memberclub.sdk.extension.ReversePerformExtension;
import com.memberclub.sdk.extension.impl.DefaultPerformExtension;
import com.memberclub.starter.AppStarter;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 掘金五阳
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppStarter.class})
public class TestExtension {

    @Autowired
    ExtensionManger extensionManger;


    @Test
    @SneakyThrows
    public void test() {
        PerformExtension extension = extensionManger.getExtension(
                BizScene.of(BizTypeEnum.DEMO_MEMBER.toBizType(),
                        SceneEnum.DEFAULT_SCENE.toString()), PerformExtension.class);
        PerformCmd cmd = new PerformCmd();
        cmd.setBizType(1);
        cmd.setUserId(1000);
        cmd.setOrderId("8342493");
        extension.execute(cmd);
    }

    @Test(expected = MemberException.class)
    @SneakyThrows
    public void testInterfaceException() {
        PerformExtension extension = extensionManger.getExtension(
                BizScene.of(BizTypeEnum.DEMO_MEMBER.toBizType(),
                        SceneEnum.DEFAULT_SCENE.toString()), DefaultPerformExtension.class);
        PerformCmd cmd = new PerformCmd();
        cmd.setBizType(1);
        cmd.setUserId(1000);
        cmd.setOrderId("8342493");
        extension.execute(cmd);
    }

    @Test(expected = MemberException.class)
    @SneakyThrows
    public void testInterfaceExceptionAndLossBaseException() {
        ReversePerformExtension extension = extensionManger.getExtension(
                BizScene.of(BizTypeEnum.DEMO_MEMBER.toBizType(),
                        SceneEnum.DEFAULT_SCENE.toString()), ReversePerformExtension.class);
        extension.execute();
    }
}