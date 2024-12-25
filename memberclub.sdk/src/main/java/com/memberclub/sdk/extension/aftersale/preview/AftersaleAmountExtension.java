/**
 * @(#)AftersaleAmountExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.aftersale.preview.ItemUsage;
import com.memberclub.domain.context.aftersale.contant.RefundWayEnum;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;

import java.util.Map;

/**
 * author: 掘金五阳
 */
public interface AftersaleAmountExtension extends BaseExtension {

    public int calculteRecommendRefundPrice(AftersalePreviewContext context, Map<String, ItemUsage> batchCode2ItemUsageMap);

    public void calculateUsageTypeByAmount(AftersalePreviewContext context);

    public RefundWayEnum calculateRefundWay(AftersalePreviewContext context);

}