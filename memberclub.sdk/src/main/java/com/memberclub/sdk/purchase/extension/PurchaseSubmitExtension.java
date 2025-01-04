/**
 * @(#)PurchaseSubmitExtension.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.extension;

import com.memberclub.domain.context.purchase.PurchaseSubmitContext;

/**
 * @author yuhaiqiang
 */
public interface PurchaseSubmitExtension {

    public void submit(PurchaseSubmitContext context);
}