/**
 * @(#)PerformDomainService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeEnum;
import com.memberclub.domain.context.aftersale.preview.ItemUsage;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.reverse.PerformItemReverseInfo;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReversePerformContext;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Service
public class PerformDomainService {


    @Autowired
    private MemberPerformItemDao memberPerformItemDao;


    @Autowired
    private PerformDataObjectBuildFactory performDataObjectBuildFactory;

    public SubOrderExtraInfo buildSubOrderExtraInfo(PerformContext context, SubOrderPerformContext subOrderPerformContext) {
        //补充
        return subOrderPerformContext.getSubOrder().getExtra();
    }


    public List<MemberPerformItemDO> queryByTradeId(long userId, String tradeId) {
        List<MemberPerformItem> items = memberPerformItemDao.selectByTradeId(userId, tradeId);
        return performDataObjectBuildFactory.toMemberPerformItemDOs(items);
    }


    public ReversePerformContext buildReversePerformContext(AfterSaleApplyContext context) {
        ReversePerformContext reversePerformContext = new ReversePerformContext();
        reversePerformContext.setBizType(context.getCmd().getBizType());
        reversePerformContext.setTradeId(context.getCmd().getTradeId());
        reversePerformContext.setUserId(context.getCmd().getUserId());
        reversePerformContext.setAfterSaleApplyContext(context);
        return reversePerformContext;
    }

    public MemberOrderDO extractMemberOrderDOFromReversePerformContext(ReversePerformContext context) {
        return context.getAfterSaleApplyContext().getPreviewContext().getMemberOrder();
    }

    public Map<String, SubOrderReversePerformContext> buildReverseContextMap(ReversePerformContext context) {
        Map<String, SubOrderReversePerformContext> skuId2SubOrderReversePerformContext = Maps.newHashMap();
        for (MemberSubOrderDO subOrder : context.getAfterSaleApplyContext().getPreviewContext().getSubOrders()) {
            SubOrderReversePerformContext subOrderReversePerformContext = new SubOrderReversePerformContext();
            subOrderReversePerformContext.setSkuId(subOrder.getSkuId());
            subOrderReversePerformContext.setSubTradeId(subOrder.getSubTradeId());
            subOrderReversePerformContext.setMemberSubOrder(subOrder);
            subOrderReversePerformContext.setItems(Lists.newArrayList());
            skuId2SubOrderReversePerformContext.put(String.valueOf(subOrder.getSubTradeId()), subOrderReversePerformContext);
        }
        return skuId2SubOrderReversePerformContext;
    }


    public void buildReversePerformAssetsInfo(ReversePerformContext context,
                                              Map<String, SubOrderReversePerformContext> subTradeId2SubOrderReversePerformContext) {
        List<PerformItemReverseInfo> items = Lists.newArrayList();

        for (Map.Entry<String, ItemUsage> entry : context.getAfterSaleApplyContext().getPreviewContext().getBatchCode2ItemUsage().entrySet()) {
            for (MemberPerformItemDO performItem : context.getAfterSaleApplyContext().getPreviewContext().getPerformItems()) {
                if (StringUtils.equals(performItem.getBatchCode(), entry.getKey())) {
                    PerformItemReverseInfo info = null;
                    if (entry.getValue().getUsageType() == UsageTypeEnum.UNUSE) {
                        info = new PerformItemReverseInfo();
                        info.setRefundType(RefundTypeEnum.ALL_REFUND);
                    }
                    if (entry.getValue().getUsageType() == UsageTypeEnum.USED) {
                        info = new PerformItemReverseInfo();
                        info.setRefundType(RefundTypeEnum.PORTION_RFUND);
                    }
                    if (info != null) {
                        info.setRightType(performItem.getRightType().getCode());
                        info.setItemToken(performItem.getItemToken());
                        info.setBatchCode(performItem.getBatchCode());
                        info.setSkuId(performItem.getSkuId());
                        items.add(info);
                    }
                }
            }
        }


        for (PerformItemReverseInfo item : items) {
            for (MemberSubOrderDO memberSubOrder : context.getAfterSaleApplyContext().getPreviewContext().getSubOrders()) {
                if (memberSubOrder.getSkuId() == item.getSkuId()) {
                    subTradeId2SubOrderReversePerformContext.get(String.valueOf(memberSubOrder.getSubTradeId())).getItems().add(item);
                }
            }
        }
        for (Map.Entry<String, SubOrderReversePerformContext> entry : subTradeId2SubOrderReversePerformContext.entrySet()) {
            boolean allRefund = entry.getValue().getItems().stream().allMatch(item -> item.getRefundType() == RefundTypeEnum.ALL_REFUND);
            entry.getValue().setAllRefund(allRefund);
        }

        return;
    }
}