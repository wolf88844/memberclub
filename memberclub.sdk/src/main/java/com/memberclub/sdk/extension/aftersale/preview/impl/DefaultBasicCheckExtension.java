/**
 * @(#)DefaultStatusCheckExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.sdk.extension.aftersale.preview.AftersaleBasicCheckExtension;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认状态验证扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultBasicCheckExtension implements AftersaleBasicCheckExtension {

    
}