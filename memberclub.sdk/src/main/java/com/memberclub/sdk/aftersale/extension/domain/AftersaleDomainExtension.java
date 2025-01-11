/**
 * @(#)AftersaleDomainExtension.java, 一月 11, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.extension.domain;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderDO;
import com.memberclub.domain.entity.AftersaleOrder;

/**
 * author: 掘金五阳
 */
public interface AftersaleDomainExtension extends BaseExtension {

    public void onSuccess(AfterSaleApplyContext context, AftersaleOrderDO order, LambdaUpdateWrapper<AftersaleOrder> wrapper);

    public void onRefundSuccess(AfterSaleApplyContext context, AftersaleOrderDO order, LambdaUpdateWrapper<AftersaleOrder> wrapper);
}