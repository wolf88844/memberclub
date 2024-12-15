/**
 * @(#)PerformMapperExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.util.TimeRange;
import com.memberclub.domain.dataobject.perform.PerformContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;

/**
 * author: 掘金五阳
 */
public interface PerformItemCalculateExtension extends BaseExtension {

    public PerformItemDO toPerformItem(SkuPerformItemConfigDO performConfigDO);

    public TimeRange buildPeriod(long baseTime, PerformItemDO itemDO);

    default String buildAssetBatchCode(PerformContext context, SkuPerformContext skuPerformContext, PerformItemDO itemDO) {
        return String.format("%s_%s_%s_%s", context.getTradeId(),
                skuPerformContext.getSkuId(),
                itemDO.getBuyIndex(),
                itemDO.getPhase());
    }
}