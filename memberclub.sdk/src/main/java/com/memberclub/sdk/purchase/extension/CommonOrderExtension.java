/**
 * @(#)CommonOrderExtension.java, 一月 05, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.dataobject.purchase.facade.CommonOrderRefundResult;
import com.memberclub.domain.dataobject.purchase.facade.CommonOrderSubmitResult;

/**
 * author: 掘金五阳
 */
public interface CommonOrderExtension extends BaseExtension {

    public void onPreSubmit(PurchaseSubmitContext context);

    public CommonOrderSubmitResult doSubmit(PurchaseSubmitContext context);

    public CommonOrderRefundResult refund(AfterSaleApplyContext context);
}