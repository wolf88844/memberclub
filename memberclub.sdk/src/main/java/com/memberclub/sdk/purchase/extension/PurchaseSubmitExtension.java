/**
 * @(#)PurchaseSubmitExtension.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "购买提单流程扩展点", type = ExtensionType.PURCHASE)
public interface PurchaseSubmitExtension extends BaseExtension {

    public void submit(PurchaseSubmitContext context);
}