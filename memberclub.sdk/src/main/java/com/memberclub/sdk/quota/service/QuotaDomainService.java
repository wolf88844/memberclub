/**
 * @(#)QuotaDomainService.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.quota.service;

import com.google.common.collect.Lists;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.usertag.UserTagDO;
import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.context.usertag.UserTagOpResponse;
import com.memberclub.domain.context.usertag.UserTagOpTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.restrict.RestrictItemType;
import com.memberclub.domain.dataobject.sku.restrict.RestrictPeriodType;
import com.memberclub.domain.dataobject.sku.restrict.RestrictUserTypeEnum;
import com.memberclub.domain.dataobject.sku.restrict.SkuRestrictItem;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.usertag.UserTagService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * author: 掘金五阳
 */
@Service
public class QuotaDomainService {

    @Autowired
    private UserTagService userTagService;

    public void validate(PurchaseSubmitContext context) {
        long userId = context.getUserId();
        List<SkuInfoDO> skus = context.getSkuInfos();

        UserTagOpCmd cmd = new UserTagOpCmd();
        //cmd.setUniqueKey();
        cmd.setOpType(UserTagOpTypeEnum.GET);
        List<UserTagOpDO> usertagOps = extractAndLoadUserTag(userId, skus, 0);
        if (CollectionUtils.isEmpty(usertagOps)) {
            CommonLog.info("当前所购商品无限额:{}",
                    CollectionUtilEx.mapToList(skus, SkuInfoDO::getSkuId));
            return;
        }
        cmd.setTags(usertagOps);

        Map<String, UserTagOpDO> key2UserTagOp = CollectionUtilEx.toMap(usertagOps, UserTagOpDO::getKey);

        UserTagOpResponse response = userTagService.operate(cmd);
        CommonLog.info("限额查询结果 cmd:{}, respone:{}", cmd, response);

        for (UserTagDO tag : response.getTags()) {
            UserTagOpDO userTagOpDO = key2UserTagOp.get(tag.getKey());
            if (tag.getCount() + userTagOpDO.getOpCount() > userTagOpDO.getTotalCount()) {
                CommonLog.warn("用户可购限额不足 skuId:{}, current:{}, opCount:{}, total:{} key:{}",
                        userTagOpDO.getSkuId(),
                        tag.getCount(),
                        userTagOpDO.getOpCount(),
                        userTagOpDO.getTotalCount(),
                        tag.getKey());
                throw ResultCode.QUOTA_LACKING.newException("用户可购限额不足");
            }
        }
        CommonLog.info("通过限额校验 tagKeys:{}", key2UserTagOp.keySet());
    }

    public void onSubmitSuccess(PurchaseSubmitContext context) {
        long userId = context.getUserId();
        List<SkuInfoDO> skus = context.getSkuInfos();

        UserTagOpCmd cmd = new UserTagOpCmd();
        cmd.setUniqueKey(context.getMemberOrder().getTradeId());
        cmd.setExpireSeconds(TimeUnit.DAYS.toMillis(1));//先暂存 1 天,履约后调整为整笔订单的有效期
        cmd.setOpType(UserTagOpTypeEnum.ADD);
        List<UserTagOpDO> usertagOps = extractAndLoadUserTag(userId, skus, cmd.getExpireSeconds());
        if (CollectionUtils.isEmpty(usertagOps)) {
            return;
        }

        cmd.setTags(usertagOps);
        try {
            UserTagOpResponse response = userTagService.operate(cmd);
            if (!response.isSuccess()) {
                CommonLog.error("提单成功后写用户购买限额失败,内部有重试! cmd:{}", cmd);
                return;
            }
            CommonLog.info("提单成功后写用户购买限额成功 cmd:{}", cmd);
        } catch (Exception e) {
            CommonLog.error("提单成功后写用户购买限额异常,内部有重试! cmd:{}", cmd);
        }

    }

    /**
     * 暂时忽略,目前无取消订单流程
     *
     * @param context
     */
    public void onCancel(PurchaseSubmitContext context) {
        /*long userId = context.getUserId();
        List<SkuInfoDO> skus = context.getSkuInfos();

        UserTagOpCmd cmd = new UserTagOpCmd();
        cmd.setUniqueKey(context.getMemberOrder().getTradeId());
        //cmd.setExpireSeconds(TimeUnit.DAYS.toMillis(1));//先暂存 1 天,履约后调整为整笔订单的有效期
        cmd.setOpType(UserTagOpTypeEnum.DEL);
        List<UserTagOpDO> usertagOps = extractAndLoadUserTag(userId, skus);
        cmd.setTags(usertagOps);
        try {
            UserTagOpResponse response = userTagService.operate(cmd);
            if (!response.isSuccess()) {
                CommonLog.error("提单成功后写用户购买限额失败,内部有重试! cmd:{}", cmd);
                return;
            }
            CommonLog.info("提单成功后写用户购买限额成功 cmd:{}", cmd);
        } catch (Exception e) {
            CommonLog.error("提单成功后写用户购买限额异常,内部有重试! cmd:{}", cmd);
        }*/
    }


    private List<UserTagOpDO> extractAndLoadUserTag(long userId,
                                                    List<SkuInfoDO> skus,
                                                    long expireSeconds) {
        List<UserTagOpDO> usertags = Lists.newArrayList();
        for (SkuInfoDO skuInfoDO : skus) {
            if (skuInfoDO.getRestrictInfo() == null ||
                    !skuInfoDO.getRestrictInfo().isEnable() ||
                    CollectionUtils.isEmpty(skuInfoDO.getRestrictInfo().getRestrictItems())) {
                continue;
            }

            List<SkuRestrictItem> restrictItems = skuInfoDO.getRestrictInfo().getRestrictItems();
            for (SkuRestrictItem restrictItem : restrictItems) {
                List<RestrictUserTypeEnum> userTypes = CollectionUtils.isEmpty(restrictItem.getUserTypes()) ?
                        Lists.newArrayList(RestrictUserTypeEnum.USERID) : restrictItem.getUserTypes();
                for (RestrictUserTypeEnum userType : userTypes) {
                    UserTagOpDO tag = new UserTagOpDO();
                    List<String> pairs = Lists.newArrayList();
                    extractAndLoadUserTypes(userId, userType, pairs);
                    extractAndLoadItemTypes(skuInfoDO, restrictItem, pairs);
                    extractAndLoadPeriodTypes(restrictItem, pairs);
                    tag.setSkuId(skuInfoDO.getSkuId());
                    tag.setKey(StringUtils.join(pairs, "_"));
                    tag.setOpCount(skuInfoDO.getBuyCount());
                    tag.setExpireSeconds(expireSeconds);
                    tag.setTotalCount(restrictItem.getTotal());
                    usertags.add(tag);
                }
            }
        }
        return usertags;
    }

    private void extractAndLoadUserTypes(long userId, RestrictUserTypeEnum userType, List<String> pairs) {
        if (userType == RestrictUserTypeEnum.USERID) {
            pairs.add(buildPair("u", userId));
        }
    }

    private void extractAndLoadItemTypes(SkuInfoDO skuInfoDO, SkuRestrictItem restrictItem, List<String> pairs) {
        if (restrictItem.getItemType() != null) {
            if (restrictItem.getItemType() == RestrictItemType.TOTAL) {
                pairs.add(buildPair("it", "total"));
            }
            if (restrictItem.getItemType() == RestrictItemType.SKU) {
                pairs.add(buildPair("it", skuInfoDO.getSkuId()));
            }
        }
    }

    private void extractAndLoadPeriodTypes(SkuRestrictItem restrictItem, List<String> pairs) {
        if (restrictItem.getPeriodType() != null) {
            if (restrictItem.getPeriodType() == RestrictPeriodType.TOTAL) {
                pairs.add(buildPair("pt", "total"));
            }
        }
    }

    public static String buildPair(String prefix, Object value) {
        return String.format("%s:%s", prefix, value.toString());
    }
}