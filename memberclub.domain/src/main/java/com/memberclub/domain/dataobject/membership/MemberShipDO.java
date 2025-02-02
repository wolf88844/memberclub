/**
 * @(#)MemberShipDO.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.membership;

import com.memberclub.domain.common.BizTypeEnum;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberShipDO {

    private Long id;

    private long userId;

    private BizTypeEnum bizType;

    private String tradeId;

    private String subTradeId;

    private String itemToken;

    private String grantCode;

    private MemberShipExtraDO extra;

    private long rightId;

    private MemberShipStatusEnum status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;

    public void onFinish() {
        status = MemberShipStatusEnum.FINISH;
        utime = System.currentTimeMillis();
    }


    public void onCancel() {
        status = MemberShipStatusEnum.CANCEL;
        utime = System.currentTimeMillis();
    }
}