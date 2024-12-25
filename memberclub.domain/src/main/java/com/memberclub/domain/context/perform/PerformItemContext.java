/**
 * @(#)PerformItemContext.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.context.perform.execute.ItemGroupGrantResult;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PerformItemContext {

    public PerformContext performContext;

    public SkuPerformContext skuPerformContext;

    private int rightType;

    public List<MemberPerformItemDO> items;

    private ItemGroupGrantResult result;
}