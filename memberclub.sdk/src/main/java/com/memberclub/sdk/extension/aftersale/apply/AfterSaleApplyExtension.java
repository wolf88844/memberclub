/**
 * @(#)AfterSaleApplyExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.apply;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.aftersale.apply.AfterSaleApplyContext;

/**
 * author: 掘金五阳
 */
public interface AfterSaleApplyExtension extends BaseExtension {

    public void apply(AfterSaleApplyContext context);
}