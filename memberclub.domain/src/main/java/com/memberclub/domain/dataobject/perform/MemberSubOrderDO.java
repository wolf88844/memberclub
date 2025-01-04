/**
 * @(#)MemberPerformHisDO.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.perform;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberSubOrderDO {

    private BizTypeEnum bizType;

    private long userId;

    private OrderSystemTypeEnum orderSystemType;

    private String orderId;

    private String tradeId;//会员单交易 ID

    private Long subOrderToken;

    private int buyCount;

    private long skuId;

    private SubOrderPerformStatusEnum status;

    private SubOrderExtraInfo extra = new SubOrderExtraInfo();

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}