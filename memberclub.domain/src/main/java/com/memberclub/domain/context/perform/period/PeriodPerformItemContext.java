/**
 * @(#)PeriodPerformItemContext.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.period;

import com.memberclub.domain.context.perform.execute.ItemGroupGrantResult;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PeriodPerformItemContext {

    private PeriodPerformContext periodPerformContext;

    private int rightType;

    public List<MemberPerformItemDO> items;

    private ItemGroupGrantResult result;
}