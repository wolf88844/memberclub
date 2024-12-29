/**
 * @(#)MemberOrderExtraInfo.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.order;

import com.memberclub.domain.dataobject.CommonUserInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberOrderExtraInfo {

    private MemberOrderLocationInfo locationInfo;

    private CommonUserInfo userInfo;

    private MemberOrderSettleInfo settleInfo;
}