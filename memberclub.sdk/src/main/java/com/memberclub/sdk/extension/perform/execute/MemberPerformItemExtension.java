/**
 * @(#)MemberPerformItemExtension.java, 十二月 16, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuPerformContext;
import com.memberclub.domain.entity.MemberPerformItem;

import java.util.List;

/**
 * @author yuhaiqiang
 */
public interface MemberPerformItemExtension extends BaseExtension {

    public List<MemberPerformItem> toMemberPerformItems(PerformItemContext performItemContext);

    public MemberPerformItem toMemberPerformItemWhenPerformSuccess(SkuPerformContext context, PerformItemDO item);

}