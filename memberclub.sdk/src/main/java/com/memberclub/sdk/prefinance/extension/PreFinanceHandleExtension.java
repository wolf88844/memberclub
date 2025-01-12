/**
 * @(#)PreFinanceHandleExtension.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.dataobject.event.trade.TradeEventDO;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "预结算流程扩展点", type = ExtensionType.PRE_FINANCE, must = true)
public interface PreFinanceHandleExtension extends BaseExtension {

    public void handle(TradeEventDO event);
}