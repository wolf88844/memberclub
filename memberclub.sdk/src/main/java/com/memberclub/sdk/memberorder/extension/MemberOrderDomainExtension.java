/**
 * @(#)MemberOrderDomainExtension.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.extension;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberOrder;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "MemberOrder Domain 层扩展点", type = ExtensionType.PURCHASE)
public interface MemberOrderDomainExtension extends BaseExtension {

    public void onSubmitSuccess(MemberOrderDO memberOrderDO, LambdaUpdateWrapper<MemberOrder> wrapper);

    public int onStartPerform(PerformContext context, LambdaUpdateWrapper<MemberOrder> wrapper);

    public void onPerformSuccess(PerformContext context, MemberOrderDO memberOrderDO, LambdaUpdateWrapper<MemberOrder> wrapper);

    public void onReversePerformSuccess(ReversePerformContext context, MemberOrderDO memberOrderDO, LambdaUpdateWrapper<MemberOrder> wrapper);
}