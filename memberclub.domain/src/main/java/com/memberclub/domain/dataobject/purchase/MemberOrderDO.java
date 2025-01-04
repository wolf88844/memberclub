/**
 * @(#)MemberOrderDO.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.purchase;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.MemberOrderPerformStatusEnum;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.order.LocationInfo;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.order.MemberOrderSettleInfo;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import lombok.Data;

import java.util.List;

/**
 * @author 掘金五阳
 */
@Data
public class MemberOrderDO {

    private BizTypeEnum bizType;

    private long userId;

    private OrderInfoDO orderInfo;

    private LocationInfo locationInfo;

    private CommonUserInfo userInfo;

    private MemberOrderSettleInfo settleInfo;

    private MemberOrderExtraInfo extra;

    private String actPriceFen;

    private String originPriceFen;

    private MemberOrderStatusEnum status;

    private MemberOrderPerformStatusEnum performStatus;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;

    private List<MemberSubOrderDO> subOrders;
}