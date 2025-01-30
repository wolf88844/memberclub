/**
 * @(#)UserTagOpCmd.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.usertag;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class UserTagOpCmd {

    public List<UserTagOpDO> tags;

    private String uniqueKey;

    private Long expireSeconds;

    private UserTagOpTypeEnum opType;
}