/**
 * @(#)PerformHisExtraInfo.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform.his;

import com.memberclub.domain.dataobject.CommonUserInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PerformHisExtraInfo {

    private PerformHisSaleInfo saleInfo = new PerformHisSaleInfo();

    private PerformHisSettleInfo settleInfo = new PerformHisSettleInfo();

    private PerformHisViewInfo viewInfo = new PerformHisViewInfo();

    private CommonUserInfo userInfo = new CommonUserInfo();
}