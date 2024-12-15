/**
 * @(#)CouponDefaultGrantExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.execute.ItemGroupGrantResult;
import com.memberclub.sdk.extension.perform.execute.PerformItemGrantExtension;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "券类型默认发放扩展点实现", bizScenes =
        {@Route(bizType = BizTypeEnum.DEMO_MEMBER, scene = SceneEnum.RIGHT_TYPE_SCENE_COUPON)})
public class CouponDefaultGrantExtension implements PerformItemGrantExtension {

    @Override
    public ItemGroupGrantResult grant(PerformItemContext context, List<PerformItemDO> items) {
        return null;
    }
}