/**
 * @(#)PerformHisExtraInfo.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform.his;

import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class SubOrderExtraInfo {

    private Boolean newmember;

    private SubOrderSaleInfo saleInfo = new SubOrderSaleInfo();

    private SubOrderFinanceInfo settleInfo = new SubOrderFinanceInfo();

    private SubOrderViewInfo viewInfo = new SubOrderViewInfo();

    private CommonUserInfo userInfo = new CommonUserInfo();

    private SkuPerformConfigDO performConfig = new SkuPerformConfigDO();
}