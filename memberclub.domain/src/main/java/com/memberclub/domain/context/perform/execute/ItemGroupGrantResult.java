/**
 * @(#)GrantResult.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.execute;

import lombok.Data;

import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class ItemGroupGrantResult {

    private Map<String, ItemGrantResult> grantMap;
}