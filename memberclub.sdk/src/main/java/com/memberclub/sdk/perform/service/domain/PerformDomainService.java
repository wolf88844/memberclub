/**
 * @(#)PerformDomainService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.service.domain;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.Lists;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.retry.Retryable;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.PerformItemContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.perform.his.PerformHisExtraInfo;
import com.memberclub.domain.dataobject.perform.his.PerformHisSaleInfo;
import com.memberclub.domain.dataobject.perform.his.PerformHisSettleInfo;
import com.memberclub.domain.dataobject.perform.his.PerformHisViewInfo;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.infrastructure.mybatis.mappers.OnceTaskDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private MemberPerformHisDao memberPerformHisDao;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Autowired
    private OnceTaskDao taskDao;

    @Transactional
    public int createOnceTask(List<OnceTask> tasks) {
        return taskDao.insertIgnoreBatch(tasks);
    }

    @Transactional
    public void performSuccess(MemberPerformHis his) {
        // TODO: 2024/12/15
        memberPerformHisDao.updateStatus(his.getUserId(),
                his.getTradeId(),
                his.getSkuId(),
                his.getStatus(),
                his.getUtime());
    }

    @Retryable
    public void itemPerformSuccess(PerformItemContext context) {
        for (MemberPerformItemDO item : context.getItems()) {
            int cnt = memberPerformItemDao.update2Status(context.getUserId(),
                    item.getItemToken(),
                    item.getBatchCode(),
                    PerformItemStatusEnum.PERFORM_SUCC.toInt());
            if (cnt <= 0) {
                MemberPerformItem itemFromDb = memberPerformItemDao.queryByItemToken(context.getUserId(),
                        item.getItemToken());
                if (!PerformItemStatusEnum.hasPerformed(itemFromDb.getStatus())) {
                    CommonLog.error("更新 item 失败 itemToken:{}", item.getItemToken());
                    ResultCode.INTERNAL_ERROR.throwException("更新 member_perform_item失败");
                }
            }
            CommonLog.info("成功更新 item 到履约完成itemToken:{}, batchCode:{}", item.getItemToken(), item.getBatchCode());
        }
    }

    @Transactional
    public int updateMemberOrderPerforming(PerformContext context) {
        int count = memberOrderDao.updateStatus(context.getUserId(),
                context.getTradeId(),
                MEMBER_ORDER_START_PERFORM.getToStatus(),
                MEMBER_ORDER_START_PERFORM.getFromStatus(),
                TimeUtil.now());
        return count;
    }

    @Transactional
    public int insertMemberPerformHis(MemberPerformHis memberPerformHis) {
        int cnt = memberPerformHisDao.insertIgnoreBatch(Lists.newArrayList(memberPerformHis));
        return cnt;
    }

    @Transactional
    public int insertMemberPerformItems(List<MemberPerformItem> items) {
        int count = memberPerformItemDao.insertIgnoreBatch(items);
        return count;
    }

    @Transactional
    public void updateMemberOrderPerformSuccess(PerformContext context) {
        int count = memberOrderDao.updateStatus2PerformSucc(context.getUserId(),
                context.getTradeId(),
                context.getStime(),
                context.getEtime(),
                MEMBER_ORDER_SUCCESS_PERFORM.getToStatus(),
                MEMBER_ORDER_SUCCESS_PERFORM.getFromStatus(),
                TimeUtil.now());
        if (count <= 0) {
            ResultCode.DATA_UPDATE_ERROR.throwException("member_order更新到成功态异常");
        }
        return;
    }

    public List<SkuBuyDetailDO> extractSkuBuyDetail(MemberOrder order) {
        List<SkuBuyDetailDO> skuBuyDetails = JsonUtils.fromJson(order.getSkuDetails()
                , new TypeReference<List<SkuBuyDetailDO>>() {
                });
        return skuBuyDetails;
    }

    public MemberOrderExtraInfo extractExtraInfO(MemberOrder order) {
        return JsonUtils.fromJson(order.getExtra(), MemberOrderExtraInfo.class);
    }

    public PerformHisExtraInfo buildPerformHisExtraInfo(PerformContext context, SkuPerformContext skuPerformContext) {
        PerformHisExtraInfo extraInfo = new PerformHisExtraInfo();

        MemberSkuSnapshotDO snapshot = skuPerformContext.getSkuBuyDetail().getSkuSnapshot();

        PerformHisViewInfo viewInfo = PerformConvertor.INSTANCE.toPerformHisViewInfo(snapshot.getViewInfo());

        PerformHisSettleInfo settleInfo = PerformConvertor.INSTANCE.toPerformHisSettleInfo(snapshot.getSettleInfo());

        PerformHisSaleInfo saleInfo = PerformConvertor.INSTANCE.toPerformHisSaleInfo(snapshot.getSaleInfo());
        extraInfo.setSettleInfo(settleInfo);
        extraInfo.setViewInfo(viewInfo);
        extraInfo.setUserInfo(context.getUserInfo());
        extraInfo.setSaleInfo(saleInfo);
        return extraInfo;
    }

}