/**
 * @(#)PerformItemReverseExtension.java, 一月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.reverse;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.reverse.SubOrderReverseInfo;
import com.memberclub.domain.context.perform.reverse.PerformItemReverseInfo;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.AssetsReverseResponse;

import java.util.List;

/**
 * author: 掘金五阳
 */
public interface AssetsReverseExtension extends BaseExtension {

    public AssetsReverseResponse reverse(ReversePerformContext context,
                                         SubOrderReverseInfo reverseInfo,
                                         List<PerformItemReverseInfo> items);

}