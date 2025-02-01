/**
 * @(#)PurchaseOrderExtraBuildExtension.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelContext;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "默认购买订单扩展属性构建 扩展点", type = ExtensionType.PURCHASE, must = true)
public interface PurchaseOrderBuildExtension extends BaseExtension {

    public void buildOrder(MemberOrderDO order, PurchaseSubmitContext context);

    public void buildSubOrder(MemberOrderDO order, MemberSubOrderDO subOrder,
                              PurchaseSubmitContext context, SkuInfoDO skuInfo);

    public void onSubmitSuccess(MemberOrderDO order, PurchaseSubmitContext context);

    public void onSubmitFail(MemberOrderDO order, PurchaseSubmitContext context, Exception e);

    public void onSubmitCancel(MemberOrderDO order, PurchaseCancelContext context);
}