/**
 * @(#)MemberOrderExtraDO.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.purchase;

import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.aftersale.ClientInfo;
import com.memberclub.domain.dataobject.order.LocationInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberOrderExtraDO {

    private LocationInfo location;

    private ClientInfo clientInfo;

    private CommonUserInfo userInfo;
    

}