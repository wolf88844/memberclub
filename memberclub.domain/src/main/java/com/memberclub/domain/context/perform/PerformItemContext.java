/**
 * @(#)PerformItemContext.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.execute.ItemGroupGrantResult;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PerformItemContext {

    private BizTypeEnum bizType;

    private long userId;

    private int rightType;

    private String tradeId;

    private String subTradeId;

    private long skuId;

    private boolean periodPerform;

    public List<MemberPerformItemDO> items;

    private ItemGroupGrantResult result;

    public BizScene toDefaultScene() {
        return BizScene.of(bizType.getCode());
    }
}