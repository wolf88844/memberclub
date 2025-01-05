/**
 * @(#)DefaultPerformItemCalculateExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.build.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.common.util.PeriodUtils;
import com.memberclub.common.util.TimeRange;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.perform.common.GrantTypeEnum;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.perform.extension.build.PerformItemCalculateExtension;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "构建履约项扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultPerformItemCalculateExtension implements PerformItemCalculateExtension {


    @Override
    public MemberPerformItemDO toPerformItem(SkuPerformItemConfigDO performConfigDO) {
        MemberPerformItemDO item = PerformConvertor.INSTANCE.toPerformItem(performConfigDO);
        item.setGrantType(GrantTypeEnum.GRANT);
        item.setStatus(PerformItemStatusEnum.INIT);
        return item;
    }

    @Override
    public TimeRange buildPeriod(long baseTime, MemberPerformItemDO itemDO) {
        if (itemDO.getPeriodType() == PeriodTypeEnum.FIX_DAY) {
            return PeriodUtils.buildTimeRangeFromBaseTime(baseTime, itemDO.getPeriodCount(), true);
        }
        return null;
    }

    @Override
    public TimeRange buildDelayPeriod(long stime, MemberPerformItemDO itemDO) {
        if (itemDO.getPeriodType() == PeriodTypeEnum.FIX_DAY) {
            return PeriodUtils.buildTimeRangeFromBaseTime(stime, itemDO.getPeriodCount(), true);
        }
        return null;
    }
}