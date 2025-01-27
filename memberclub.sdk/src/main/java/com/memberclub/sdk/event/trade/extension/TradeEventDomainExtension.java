/**
 * @(#)TradeEventDomainExtension.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.event.trade.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.period.PeriodPerformContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "TradeEvent 事件构建扩展点", type = ExtensionType.COMMON, must = true)
public interface TradeEventDomainExtension extends BaseExtension {

    public String onPerformSuccessForSubOrder(PerformContext performContext,
                                              SubOrderPerformContext subOrderPerformContext,
                                              MemberSubOrderDO subOrder,
                                              TradeEventDO event);


    public String onPeriodPerformSuccessForSubOrder(PeriodPerformContext context, TradeEventDO event);

    public String onReversePerformSuccessForSubOrder(ReversePerformContext context,
                                                     SubOrderReversePerformContext subOrderReversePerformContext,
                                                     MemberSubOrderDO subOrder, TradeEventDO event);

    public String onRefundSuccessForSubOrder(AfterSaleApplyContext context,
                                             MemberSubOrderDO subOrder,
                                             TradeEventDO event);
}