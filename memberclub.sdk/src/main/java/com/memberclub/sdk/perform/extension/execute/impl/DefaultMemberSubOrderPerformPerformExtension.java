/**
 * @(#)DefaultMemberPerformHisExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.execute.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.perform.extension.execute.MemberSubOrderPerformExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认履约上下文构建", bizScenes = {@Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)})
public class DefaultMemberSubOrderPerformPerformExtension implements MemberSubOrderPerformExtension {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public MemberSubOrder toMemberSubOrder(PerformContext context, SkuPerformContext skuPerformContext) {
        MemberSubOrder memberSubOrder = PerformConvertor.INSTANCE.toMemberSubOrder(skuPerformContext.getHis());
        return memberSubOrder;
    }

    @Override
    public MemberSubOrder toMemberSubOrderWhenPerformSuccess(PerformContext context, SkuPerformContext skuPerformContext) {
        MemberSubOrder memberSubOrder = toMemberSubOrder(context, skuPerformContext);
        memberSubOrder.setStatus(SubOrderPerformStatusEnum.PERFORM_SUCC.toInt());
        memberSubOrder.setUtime(TimeUtil.now());
        return memberSubOrder;
    }

    @Override
    public SubOrderExtraInfo toCommonExtraInfo(PerformContext context, SkuPerformContext skuPerformContext) {
        SubOrderExtraInfo extraInfo = performDomainService.buildSubOrderExtraInfo(context, skuPerformContext);

        return extraInfo;
    }


}