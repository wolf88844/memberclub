/**
 * @(#)PerformDomainService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.contant.UsageTypeEnum;
import com.memberclub.domain.context.aftersale.preview.ItemUsage;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.perform.reverse.PerformItemReverseInfo;
import com.memberclub.domain.context.perform.reverse.ReversePerformContext;
import com.memberclub.domain.context.perform.reverse.SubOrderReverseInfo;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
import com.memberclub.domain.dataobject.perform.his.SubOrderExtraInfo;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.OnceTaskDao;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.memberclub.domain.common.MemberTradeEvent.MEMBER_ORDER_START_PERFORM;
import static com.memberclub.domain.common.MemberTradeEvent.MEMBER_ORDER_SUCCESS_PERFORM;

/**
 * author: 掘金五阳
 */
@Service
public class PerformDomainService {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Autowired
    private OnceTaskDao taskDao;

    @Autowired
    private PerformDataObjectBuildFactory performDataObjectBuildFactory;

    @Transactional
    public int createOnceTask(List<OnceTask> tasks) {
        return taskDao.insertIgnoreBatch(tasks);
    }

    @Transactional
    public void finishSubOrderPerformOnSuccess(MemberSubOrder subOrder) {
        // TODO: 2024/12/15
        memberSubOrderDao.updateStatus(subOrder.getUserId(),
                subOrder.getSubTradeId(),
                subOrder.getPerformStatus(),
                subOrder.getUtime());
    }

    @Transactional
    public int startPerformSubOrder(MemberSubOrder subOrder) {
        // TODO: 2024/12/15
        return memberSubOrderDao.updateStatus(subOrder.getUserId(),
                subOrder.getSubTradeId(),
                subOrder.getPerformStatus(),
                subOrder.getUtime());
    }

    @Retryable
    public void finishPerformItem(PerformItemContext context) {
        for (MemberPerformItemDO item : context.getItems()) {
            int cnt = memberPerformItemDao.updateAssetbatchAndStatus(context.getUserId(),
                    item.getItemToken(),
                    item.getBatchCode(),
                    PerformItemStatusEnum.PERFORM_SUCC.getCode());
            if (cnt <= 0) {
                MemberPerformItem itemFromDb = memberPerformItemDao.queryByItemToken(context.getUserId(),
                        item.getItemToken());
                if (!PerformItemStatusEnum.hasPerformed(itemFromDb.getStatus())) {
                    CommonLog.error("更新 item 失败 itemToken:{}", item.getItemToken());
                    throw ResultCode.INTERNAL_ERROR.newException("更新 member_perform_item失败");
                }
            }
            CommonLog.info("成功更新 item 到履约完成itemToken:{}, batchCode:{}", item.getItemToken(), item.getBatchCode());
        }
    }

    @Transactional
    public int startPerformMemberOrder(PerformContext context) {
        int count = memberOrderDao.updatePerformStatus(context.getUserId(),
                context.getTradeId(),
                MEMBER_ORDER_START_PERFORM.getToStatus(),
                MEMBER_ORDER_START_PERFORM.getFromStatus(),
                TimeUtil.now());
        return count;
    }

    @Transactional
    public int insertMemberSubOrder(MemberSubOrder memberSubOrder) {
        int cnt = memberSubOrderDao.insertIgnoreBatch(Lists.newArrayList(memberSubOrder));
        return cnt;
    }

    @Transactional
    public int insertMemberPerformItems(List<MemberPerformItem> items) {
        int count = memberPerformItemDao.insertIgnoreBatch(items);
        return count;
    }

    @Transactional
    public void finishMemberOrderPerformOnSuccess(PerformContext context) {
        int count = memberOrderDao.updateStatus2PerformSucc(context.getUserId(),
                context.getTradeId(),
                context.getStime(),
                context.getEtime(),
                context.getMemberOrder().getStatus().getCode(),
                MEMBER_ORDER_SUCCESS_PERFORM.getToStatus(),
                MEMBER_ORDER_SUCCESS_PERFORM.getFromStatus(),
                TimeUtil.now());
        if (count <= 0) {
            throw ResultCode.DATA_UPDATE_ERROR.newException("member_order 更新到成功态异常");
        }
        return;
    }

    public boolean isFinishReverseMemberSubOrder(ReversePerformContext context,
                                                 SubOrderReverseInfo info) {
        int status = generateMemberSubOrderFinishReverseStatus(context);

        MemberSubOrder his = memberSubOrderDao.selectBySkuId(context.getUserId(), context.getTradeId(), info.getSkuId());
        if (his.getPerformStatus() == status) {
            CommonLog.info("已经完成更新履约单 [{}]", his);
            return true;
        }
        return false;
    }


    @Transactional
    public void startReversePerformMemberSubOrder(ReversePerformContext context,
                                                  SubOrderReverseInfo info) {
        int cnt = memberSubOrderDao.updateStatus(context.getUserId(), info.getSubTradeId(),
                SubOrderPerformStatusEnum.REVEREING.getCode(), TimeUtil.now());
        CommonLog.info("更新履约单状态为逆向履约中:{}", cnt);
    }

    @Transactional
    public void finishReversePerformMemberSubOrder(ReversePerformContext context,
                                                   SubOrderReverseInfo info) {
        int status = generateMemberSubOrderFinishReverseStatus(context);

        memberSubOrderDao.updateStatus(context.getUserId(), info.getSubTradeId(),
                status, TimeUtil.now());
        CommonLog.info("更新履约单状态为逆向履约完成");
    }

    private int generateMemberSubOrderFinishReverseStatus(ReversePerformContext context) {
        boolean allRefund = context.getCurrentSubOrderReverseInfo().isAllRefund();
        return allRefund ? SubOrderPerformStatusEnum.COMPLETED_REVERSED.getCode() :
                SubOrderPerformStatusEnum.PORTION_REVERSED.getCode();
    }

    public boolean isFinishReverseMemberPerformItems(ReversePerformContext context,
                                                     SubOrderReverseInfo hisInfo,
                                                     List<PerformItemReverseInfo> infos) {
        List<String> itemTokens = infos.stream().map(PerformItemReverseInfo::getItemToken).collect(Collectors.toList());
        boolean allFinish = true;
        List<MemberPerformItem> items = memberPerformItemDao.queryByItemTokens(context.getUserId(), itemTokens);
        for (PerformItemReverseInfo info : infos) {
            int status = generatePerformItemFinishReverseStatus(info);
            boolean finish = false;
            for (MemberPerformItem item : items) {
                if (StringUtils.equals(info.getItemToken(), item.getItemToken()) &&
                        item.getStatus() == status) {
                    finish = true;
                }
            }
            if (!finish) {
                allFinish = false;
            }
        }
        if (allFinish) {
            return true;
        }
        return false;
    }

    @Transactional
    public void startReverseMemberPerformItems(ReversePerformContext context,
                                               SubOrderReverseInfo hisInfo,
                                               List<PerformItemReverseInfo> infos) {
        for (PerformItemReverseInfo info : infos) {
            memberPerformItemDao.updateStatus(context.getUserId(),
                    info.getItemToken(), PerformItemStatusEnum.REVEREING.getCode(), TimeUtil.now());
        }
        CommonLog.info("更新履约项状态为逆向履约中");
    }


    @Transactional
    public void finishReverseMemberPerformItems(ReversePerformContext context,
                                                SubOrderReverseInfo hisInfo,
                                                List<PerformItemReverseInfo> infos) {
        for (PerformItemReverseInfo info : infos) {
            int status = generatePerformItemFinishReverseStatus(info);
            memberPerformItemDao.updateStatus(context.getUserId(),
                    info.getItemToken(), status, TimeUtil.now());
        }
        CommonLog.info("更新履约项状态为逆向履约完成");
    }

    private int generatePerformItemFinishReverseStatus(PerformItemReverseInfo info) {
        return info.getRefundType() == RefundTypeEnum.ALL_REFUND ? PerformItemStatusEnum.COMPLETED_REVERSED.getCode() :
                PerformItemStatusEnum.PORTION_REVERSED.getCode();
    }


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

    public Map<Long, SubOrderReverseInfo> buildSubOrderReversePerformInfoMapBaseAssets(ReversePerformContext context) {
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

        Map<Long, SubOrderReverseInfo> skuId2HisInfos = Maps.newHashMap();
        for (PerformItemReverseInfo item : items) {
            for (MemberSubOrderDO memberSubOrder : context.getAfterSaleApplyContext().getPreviewContext().getSubOrders()) {
                if (memberSubOrder.getSkuId() == item.getSkuId()) {
                    if (skuId2HisInfos.containsKey(item.getSkuId())) {
                        skuId2HisInfos.get(item.getSkuId()).getItems().add(item);
                    } else {
                        SubOrderReverseInfo subOrderReverseInfo = new SubOrderReverseInfo();
                        subOrderReverseInfo.setSkuId(item.getSkuId());
                        subOrderReverseInfo.setSubTradeId(memberSubOrder.getSubTradeId());
                        subOrderReverseInfo.setMemberSubOrder(memberSubOrder);
                        subOrderReverseInfo.setItems(Lists.newArrayList(item));
                        skuId2HisInfos.put(item.getSkuId(), subOrderReverseInfo);
                    }
                }
            }
        }
        for (Map.Entry<Long, SubOrderReverseInfo> entry : skuId2HisInfos.entrySet()) {
            boolean allRefund = entry.getValue().getItems().stream().allMatch(item -> item.getRefundType() == RefundTypeEnum.ALL_REFUND);
            entry.getValue().setAllRefund(allRefund);
        }

        return skuId2HisInfos;
    }
}