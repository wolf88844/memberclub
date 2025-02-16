/**
 * @(#)NewMemberDO.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.sku;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class SkuNewMemberInfo {

    private boolean newMemberMarkEnable;

    private List<UserTypeEnum> userTypes;
}