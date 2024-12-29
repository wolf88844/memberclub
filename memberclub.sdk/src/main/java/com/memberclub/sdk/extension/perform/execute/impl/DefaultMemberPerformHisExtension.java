/**
 * @(#)DefaultMemberPerformHisExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.MemberPerformHisStatusEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.perform.his.PerformHisExtraInfo;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.extension.perform.execute.MemberPerformHisExtension;
import com.memberclub.sdk.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认履约上下文构建", bizScenes = {@Route(bizType = BizTypeEnum.DEFAULT, scenes = SceneEnum.DEFAULT_SCENE)})
public class DefaultMemberPerformHisExtension implements MemberPerformHisExtension {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public MemberPerformHis toMemberPerformHis(PerformContext context, SkuPerformContext skuPerformContext) {
        MemberPerformHis memberPerformHis = PerformConvertor.INSTANCE.toMemberPerformHis(skuPerformContext.getHis());
        return memberPerformHis;
    }

    @Override
    public MemberPerformHis toMemberPerformHisWhenPerformSuccess(PerformContext context, SkuPerformContext skuPerformContext) {
        MemberPerformHis memberPerformHis = toMemberPerformHis(context, skuPerformContext);
        memberPerformHis.setStatus(MemberPerformHisStatusEnum.PERFORM_SUCC.toInt());
        memberPerformHis.setUtime(TimeUtil.now());
        return memberPerformHis;
    }

    @Override
    public PerformHisExtraInfo toCommonExtraInfo(PerformContext context, SkuPerformContext skuPerformContext) {
        PerformHisExtraInfo extraInfo = performDomainService.buildPerformHisExtraInfo(context, skuPerformContext);

        return extraInfo;
    }


}