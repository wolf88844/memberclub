/**
 * @(#)InventoryCacheCmd.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.inventory;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class InventoryCacheCmd {

    List<InventoryCacheDO> caches;
}