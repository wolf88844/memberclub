/**
 * @(#)MemberOrderDomainExtension.java, 一月 08, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.memberorder.extension;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberOrder;

/**
 * author: 掘金五阳
 */
public interface MemberOrderDomainExtension extends BaseExtension {

    public void onSubmitSuccess(MemberOrderDO memberOrderDO, LambdaUpdateWrapper<MemberOrder> wrapper);
}