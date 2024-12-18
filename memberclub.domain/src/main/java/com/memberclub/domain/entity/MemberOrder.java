/**
 * @(#)MemberOrder.java, 十二月 14, 2024.
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
public class MemberOrder {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private long userId;

    private int orderSystemType;

    private String orderId;

    private String tradeId;

    private String userInfo;

    private String skuDetails;

    private String extra;

    private String actPriceFen;

    private String originPriceFen;

    private int status;

    private long stime;

    private long etime;

    private long utime;

    private long ctime;
}