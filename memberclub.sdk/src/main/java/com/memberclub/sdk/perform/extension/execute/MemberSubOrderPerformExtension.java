/**
 * @(#)MemberPerformHisExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.extension.execute;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.entity.MemberSubOrder;

/**
 * author: 掘金五阳
 */
public interface MemberSubOrderPerformExtension extends BaseExtension {

    public MemberSubOrder toMemberSubOrder(PerformContext context, SubOrderPerformContext subOrderPerformContext);

    public MemberSubOrder toMemberSubOrderWhenPerformSuccess(PerformContext context, SubOrderPerformContext subOrderPerformContext);


    public void buildCommonExtraInfo(PerformContext context, SubOrderPerformContext subOrderPerformContext);
}
