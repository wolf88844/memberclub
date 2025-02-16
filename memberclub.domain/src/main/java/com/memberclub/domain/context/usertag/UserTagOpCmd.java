/**
 * @(#)UserTagOpCmd.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.usertag;

import com.memberclub.domain.common.BizTypeEnum;
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

    public void buildUniqueKey(UserTagTypeEnum userTagType, BizTypeEnum bizType, String uniqueKey) {
        String key = String.format("%s:%s_%s:%s_%s:%s",
                UserTagKeyEnum.USER_TAG_TYPE.getName(), userTagType.name(),
                UserTagKeyEnum.BIZTYPE.getName(), bizType.getCode(),
                UserTagKeyEnum.UNIQUE_KEY.getName(), uniqueKey);
        setUniqueKey(key);
    }
}