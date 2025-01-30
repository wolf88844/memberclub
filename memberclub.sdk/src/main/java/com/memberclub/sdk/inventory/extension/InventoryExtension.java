/**
 * @(#)InventoryExtension.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.inventory.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.inventory.InventoryOpContext;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "库存扩展点", must = false, type = ExtensionType.PURCHASE)
public interface InventoryExtension extends BaseExtension {

    public void validate(InventoryOpContext context);

    public void operate(InventoryOpContext context);

    public void buildSubKey(InventoryOpContext context);
}