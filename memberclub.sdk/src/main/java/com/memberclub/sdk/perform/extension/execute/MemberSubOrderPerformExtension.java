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

/**
 * author: 掘金五阳
 */
@Deprecated
public interface MemberSubOrderPerformExtension extends BaseExtension {

    public void buildMemberSubOrderOnStartPerform(PerformContext context, SubOrderPerformContext subOrderPerformContext);

    public void buildMemberSubOrderWhenPerformSuccess(PerformContext context, SubOrderPerformContext subOrderPerformContext);


    public void buildCommonExtraInfoOnPrePerform(PerformContext context, SubOrderPerformContext subOrderPerformContext);
}
