/**
 * @(#)MemberPerformItem.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * @author 掘金五阳
 */
@Data
public class MemberPerformItem {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private long userId;

    private String tradeId;

    private long skuId;

    private long rightId;

    private int providerId;

    private String grantInfo;

    private String viewInfo;

    private String settleInfo;

    private String saleInfo;


    private int rightType;

    private String batchCode;

    private String itemToken;

    private int assetCount;

    private int phase;

    private int cycle;

    private int buyIndex;

    /***
     * 0 发放
     * 1 激活
     */
    private int grantType;

    private int status;

    private String extra;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}