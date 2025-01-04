/**
 * @(#)DefaultMemberPerformHisExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.execute.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.perform.extension.execute.MemberSubOrderPerformExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认履约上下文构建", bizScenes = {@Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)})
public class DefaultMemberSubOrderPerformPerformExtension implements MemberSubOrderPerformExtension {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public MemberSubOrder toMemberSubOrder(PerformContext context, SubOrderPerformContext subOrderPerformContext) {
        MemberSubOrder memberSubOrder = PerformConvertor.INSTANCE.toMemberSubOrder(subOrderPerformContext.getSubOrder());
        return memberSubOrder;
    }

    @Override
    public MemberSubOrder toMemberSubOrderWhenPerformSuccess(PerformContext context, SubOrderPerformContext subOrderPerformContext) {
        MemberSubOrder memberSubOrder = toMemberSubOrder(context, subOrderPerformContext);
        memberSubOrder.setStatus(SubOrderPerformStatusEnum.PERFORM_SUCC.toInt());
        memberSubOrder.setUtime(TimeUtil.now());
        return memberSubOrder;
    }

    @Override
    public SubOrderExtraInfo toCommonExtraInfo(PerformContext context, SubOrderPerformContext subOrderPerformContext) {
        SubOrderExtraInfo extraInfo = performDomainService.buildSubOrderExtraInfo(context, subOrderPerformContext);

        return extraInfo;
    }


}