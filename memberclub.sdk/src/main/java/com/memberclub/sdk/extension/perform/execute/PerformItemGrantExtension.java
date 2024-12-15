/**
 * @(#)PerformItemGrantExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.execute.ItemGroupGrantResult;

import java.util.List;

/**
 * author: 掘金五阳
 */
public interface PerformItemGrantExtension extends BaseExtension {

    public ItemGroupGrantResult grant(PerformItemContext context, List<PerformItemDO> items);
}