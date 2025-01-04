/**
 * @(#)PerformContext.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.common.RetrySourceEunm;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberSubOrder;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PerformContext {

    private PerformCmd cmd;

    private RetrySourceEunm retrySource;

    private boolean skipPerform;

    private BizTypeEnum bizType;

    private CommonUserInfo userInfo;

    private long userId;

    /******************订单信息****************/
    private String orderId;

    private String tradeId;

    private MemberOrder memberOrder;

    private MemberOrderExtraInfo memberOrderExtraInfo;

    private OrderSystemTypeEnum orderSystemType;

    private String actPriceFen;

    private String originPriceFen;

    private int memberOrderStartPerformUpdateCount;

    private long stime;

    private long etime;

    /******************订单信息****************/

    /****************** Start 商品和履约配置****************/


    /**
     * 只读,不能写
     */
    private List<MemberSubOrder> hisListFromDb;

    private List<SkuInfoDO> skuBuyDetails;

    private List<SkuPerformContext> skuPerformContexts;

    private SkuPerformContext currentSkuPerformContext;

    private long immediatePerformEtime;

    private long delayPerformEtime;

    /******************商品和履约配置****************/


    /********************* Start 重试上下文***************/
    private int retryTimes;

    private String lockValue;

    private long baseTime;

    /********************* 重试上下文***************/

    public BizScene toDefaultScene() {
        return BizScene.of(bizType.toBizType());
    }

    public MemberSubOrder matchHisFromDb(long skuId) {
        if (!CollectionUtils.isEmpty(hisListFromDb)) {
            for (MemberSubOrder memberSubOrder : hisListFromDb) {
                if (memberSubOrder.getSkuId() == skuId) {
                    return memberSubOrder;
                }
            }
        }
        return null;
    }
}