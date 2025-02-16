/**
 * @(#)UserTagCacheService.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.usertag;

import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpResponse;

/**
 * author: 掘金五阳
 */
public interface UserTagService {

    public UserTagOpResponse operate(UserTagOpCmd cmd);
}