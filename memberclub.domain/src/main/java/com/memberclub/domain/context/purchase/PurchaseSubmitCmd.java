/**
 * @(#)PurchaseSubmitCmd.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.purchase;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.purchase.common.PurchaseSourceEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.aftersale.ClientInfo;
import com.memberclub.domain.dataobject.order.LocationInfo;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PurchaseSubmitCmd {

    private BizTypeEnum bizType;

    private CommonUserInfo userInfo;

    private ClientInfo clientInfo;

    private LocationInfo locationInfo;

    private PurchaseSourceEnum source;

    private String previewToken;

    private List<PurchaseSkuSubmitCmd> skus;
}