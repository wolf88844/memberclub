/**
 * @(#)PurchaseSubmitExtension.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelContext;

/**
 * @author wuyang
 */
@ExtensionConfig(desc = "购买流程扩展点", type = ExtensionType.PURCHASE, must = true)
public interface PurchaseExtension extends BaseExtension {

    public void submit(PurchaseSubmitContext context);

    public void reverse(AfterSaleApplyContext context);

    public void cancel(PurchaseCancelContext context);
}