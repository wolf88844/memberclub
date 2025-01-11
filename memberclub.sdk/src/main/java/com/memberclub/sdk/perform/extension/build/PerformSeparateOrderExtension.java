/**
 * @(#)BuildPerformContextExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.build;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.PerformContext;

/**
 * author: 掘金五阳
 */
public interface PerformSeparateOrderExtension extends BaseExtension {

    public void separateOrder(PerformContext context);

}