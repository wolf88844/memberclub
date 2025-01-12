/**
 * @(#)MemberSubOrderDomainExtension.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.extension;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.entity.MemberSubOrder;

/**
 * author: 掘金五阳
 */
public interface MemberSubOrderDomainExtension extends BaseExtension {

    public void onSubmitSuccess(MemberSubOrderDO memberSubOrderDO, LambdaUpdateWrapper<MemberSubOrder> wrapper);


    /**
     * 开始履约 会员子单
     *
     * @param performContext
     * @param subOrderPerformContext
     * @param memberSubOrderDO
     * @param wrapper
     */
    public void onStartPerform(PerformContext performContext,
                               SubOrderPerformContext subOrderPerformContext,
                               MemberSubOrderDO memberSubOrderDO,
                               LambdaUpdateWrapper<MemberSubOrder> wrapper);

    public void onPerformSuccess(PerformContext performContext,
                                 SubOrderPerformContext subOrderPerformContext,
                                 MemberSubOrderDO memberSubOrderDO,
                                 LambdaUpdateWrapper<MemberSubOrder> wrapper);

    public void onStartReversePerform(ReversePerformContext context,
                                      SubOrderReversePerformContext subOrderReversePerformContext,
                                      MemberSubOrderDO subOrder,
                                      LambdaUpdateWrapper<MemberSubOrder> wrapper);

    public void onReversePerformSuccess(ReversePerformContext context,
                                        SubOrderReversePerformContext subOrderReversePerformContext,
                                        MemberSubOrderDO subOrder,
                                        LambdaUpdateWrapper<MemberSubOrder> wrapper);

    public void onRefundSuccess(AfterSaleApplyContext context,
                                MemberSubOrderDO subOrder,
                                LambdaUpdateWrapper<MemberSubOrder> wrapper);


}