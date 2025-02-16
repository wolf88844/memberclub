/**
 * @(#)UserTagResponse.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.usertag;

import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class UserTagOpResponse {

    public boolean success;

    private List<UserTagDO> tags;
}