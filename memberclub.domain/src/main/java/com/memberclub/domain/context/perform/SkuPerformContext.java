/**
 * @(#)SkuPerformContext.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformPropDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SkuPerformContext {

    private long skuId;

    private long buyCount;

    private String performHisToken;

    private long stime;

    private long etime;

    private SkuPerformPropDO extra;

    private List<MemberPerformItemDO> immediatePerformItems;

    private List<MemberPerformItemDO> delayPerformItems;
}