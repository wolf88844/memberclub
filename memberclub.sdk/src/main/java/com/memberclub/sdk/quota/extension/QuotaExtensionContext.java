/**
 * @(#)QuotaExtensionContext.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.quota.extension;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class QuotaExtensionContext {
    long userId;

    private BizTypeEnum bizType;

    List<SkuInfoDO> skus;

    List<UserTagOpDO> userTagOpDOList;

}