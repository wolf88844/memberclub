/**
 * @(#)AftersalePreviewContext.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.preview;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.collect.Maps;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.contant.RefundWayEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeCalculateTypeEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeEnum;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
@Data
public class AftersalePreviewContext {

    private boolean previewBeforeApply;

    /***************** 基础履约数据 *********************/
    private AfterSalePreviewCmd cmd;

    private MemberOrderDO memberOrder;

    List<MemberSubOrderDO> subOrders;

    List<MemberPerformItemDO> performItems;

    private Integer periodIndex;

    /******************售后预览中间数据****************************/


    private long stime;

    private long etime;


    /******************临时数据****************************/

    private MemberSubOrderDO currentSubOrderDO;

    List<MemberPerformItemDO> currentPerformItemsGroupByRightType;

    private Map<String, ItemUsage> currentBatchCode2ItemUsage;

    int currentRightType;


    /******************券包使用情况****************************/
    /****
     * 使用类型的计算方式
     */
    private UsageTypeCalculateTypeEnum usageTypeCalculateType;


    private UsageTypeEnum usageType;

    private int usedPriceFen;


    private Map<String, ItemUsage> batchCode2ItemUsage = Maps.newHashMap();

    /******************赔付金额相关数据****************************/

    private int payPriceFen;

    private int actRefundPrice;

    private int recommendRefundPrice;

    private RefundTypeEnum refundType;

    private RefundWayEnum refundWay;

    private String digests;

    private Integer digestVersion;

    /*****************售后其他数据***********************************/


    private int aftersaleTimesCurrentDay;

    /***************** 售后预览结果 *********************/
    private AftersaleUnableCode unableCode;

    private String unableTip;


    public BizScene toDefaultBizScene() {
        return BizScene.of(getCmd().getBizType().getCode());
    }

}