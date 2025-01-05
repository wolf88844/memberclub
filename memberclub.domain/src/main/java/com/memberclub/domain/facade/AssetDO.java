/**
 * @(#)CouponDO.java, 十二月 18, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.facade;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class AssetDO {

    private long userId;

    private long assetId;

    private int rightType;

    private String batchCode;

    private int priceFen;

    private long stime;

    private long etime;

    private int status;

    private int assetType;

    private long ctime;

    public boolean isUsed() {
        return status == AssetStatusEnum.USED.getCode();
    }

    public boolean isReversed() {
        return status == AssetStatusEnum.FREEZE.getCode();
    }

    /**
     * 正常资产(非废弃,冻结类资产)
     *
     * @return
     */
    public boolean isNormal() {
        return true;
    }

}