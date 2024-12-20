/**
 * @(#)DefaultIdGenerator.java, 十二月 19, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.id.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.infrastructure.id.IdGenerator;
import org.apache.commons.lang3.RandomStringUtils;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认 Id 生成器", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)
})
public class DefaultIdGenerator implements IdGenerator {

    @Override
    public String generateId() {
        // TODO: 2024/12/19 修改为其他 ID 生成器
        return RandomStringUtils.randomNumeric(20);
    }
}