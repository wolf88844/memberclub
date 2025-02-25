/**
 * @(#)TradeEventDomainExtension.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
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
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelContext;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;

/**
 * TradeEventDomainExtension 定义了与交易事件相关的扩展点接口。
 * 该接口提供了多个方法，用于在不同业务场景下构建和处理交易事件。
 *
 * @author wuyang
 */
@ExtensionConfig(desc = "TradeEvent 事件构建扩展点", type = ExtensionType.COMMON, must = true)
public interface TradeEventDomainExtension extends BaseExtension {

    /**
     * 处理子订单取消成功后的交易事件。
     * 当子订单取消成功时，调用此方法生成相应的交易事件信息。
     *
     * @param cancelContext 取消上下文，包含取消操作的相关信息
     * @param memberOrderDO 主订单对象，包含主订单的详细信息
     * @param subOrder 子订单对象，包含子订单的详细信息
     * @param event 交易事件对象，用于记录和传递事件信息
     * @return 生成的交易事件描述信息
     */
    public String onPurchaseCancelSuccessForSubOrder(PurchaseCancelContext cancelContext, MemberOrderDO memberOrderDO,
                                                     MemberSubOrderDO subOrder,
                                                     TradeEventDO event);

    /**
     * 处理子订单执行成功后的交易事件。
     * 当子订单执行成功时，调用此方法生成相应的交易事件信息。
     *
     * @param performContext 执行上下文，包含执行操作的相关信息
     * @param subOrderPerformContext 子订单执行上下文，包含子订单执行的详细信息
     * @param subOrder 子订单对象，包含子订单的详细信息
     * @param event 交易事件对象，用于记录和传递事件信息
     * @return 生成的交易事件描述信息
     */
    public String onPerformSuccessForSubOrder(PerformContext performContext,
                                              SubOrderPerformContext subOrderPerformContext,
                                              MemberSubOrderDO subOrder,
                                              TradeEventDO event);

    /**
     * 处理周期性执行成功后的交易事件。
     * 当周期性执行成功时，调用此方法生成相应的交易事件信息。
     *
     * @param context 周期执行上下文，包含周期执行的相关信息
     * @param event 交易事件对象，用于记录和传递事件信息
     * @return 生成的交易事件描述信息
     */
    public String onPeriodPerformSuccessForSubOrder(PeriodPerformContext context, TradeEventDO event);

    /**
     * 处理反向执行成功后的交易事件。
     * 当反向执行成功时，调用此方法生成相应的交易事件信息。
     *
     * @param context 反向执行上下文，包含反向执行的相关信息
     * @param subOrderReversePerformContext 子订单反向执行上下文，包含子订单反向执行的详细信息
     * @param subOrder 子订单对象，包含子订单的详细信息
     * @param event 交易事件对象，用于记录和传递事件信息
     * @return 生成的交易事件描述信息
     */
    public String onReversePerformSuccessForSubOrder(ReversePerformContext context,
                                                     SubOrderReversePerformContext subOrderReversePerformContext,
                                                     MemberSubOrderDO subOrder, TradeEventDO event);

    /**
     * 处理退款成功后的交易事件。
     * 当退款成功时，调用此方法生成相应的交易事件信息。
     *
     * @param context 售后申请上下文，包含售后申请的相关信息
     * @param subOrder 子订单对象，包含子订单的详细信息
     * @param event 交易事件对象，用于记录和传递事件信息
     * @return 生成的交易事件描述信息
     */
    public String onRefundSuccessForSubOrder(AfterSaleApplyContext context,
                                             MemberSubOrderDO subOrder,
                                             TradeEventDO event);
}
