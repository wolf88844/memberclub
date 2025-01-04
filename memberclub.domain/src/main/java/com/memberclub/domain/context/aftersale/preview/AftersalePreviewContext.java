/**
 * @(#)AftersalePreviewContext.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.aftersale.preview;

import com.google.common.collect.Maps;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.contant.RefundWayEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeCalculateTypeEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeEnum;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.entity.MemberPerformItem;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Data
public class AftersalePreviewContext {

    private boolean previewBeforeApply;

    /***************** 基础履约数据 *********************/
    private AfterSalePreviewCmd cmd;

    private MemberOrder memberOrder;

    List<MemberSubOrder> subOrderList;

    List<MemberPerformItem> performItems;

    /******************售后预览中间数据****************************/


    private long stime;

    private long etime;


    /******************临时数据****************************/
    List<MemberPerformItem> currentPerformItemsGroupByRightType;

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