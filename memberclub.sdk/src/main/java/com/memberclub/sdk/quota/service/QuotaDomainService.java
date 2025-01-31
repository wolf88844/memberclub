/**
 * @(#)QuotaDomainService.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.quota.service;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.CollectionUtilEx;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.usertag.UserTagDO;
import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.context.usertag.UserTagOpResponse;
import com.memberclub.domain.context.usertag.UserTagOpTypeEnum;
import com.memberclub.domain.context.usertag.UserTagTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.usertag.UserTagService;
import com.memberclub.sdk.quota.extension.QuotaExtension;
import com.memberclub.sdk.quota.extension.QuotaExtensionContext;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Service
public class QuotaDomainService {

    @Autowired
    private UserTagService userTagService;

    @Autowired
    private ExtensionManager extensionManager;

    public void validate(PurchaseSubmitContext context) {
        long userId = context.getUserId();
        List<SkuInfoDO> skus = context.getSkuInfos();

        UserTagOpCmd cmd = new UserTagOpCmd();
        //cmd.setUniqueKey();
        cmd.setOpType(UserTagOpTypeEnum.GET);

        QuotaExtensionContext quotaExtensionContext = new QuotaExtensionContext();
        quotaExtensionContext.setUserId(userId);
        quotaExtensionContext.setBizType(context.getBizType());
        quotaExtensionContext.setSkus(skus);

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                QuotaExtension.class).buildUserTagOp(quotaExtensionContext);
        List<UserTagOpDO> usertagOps = quotaExtensionContext.getUserTagOpDOList();

        if (CollectionUtils.isEmpty(usertagOps)) {
            CommonLog.info("当前所购商品无限额:{}", CollectionUtilEx.mapToList(skus, SkuInfoDO::getSkuId));
            return;
        }
        cmd.setTags(usertagOps);

        Map<String, UserTagOpDO> key2UserTagOp = CollectionUtilEx.toMap(usertagOps, UserTagOpDO::getKey);

        UserTagOpResponse response = null;
        try {
            response = userTagService.operate(cmd);
        } catch (Exception e) {
            CommonLog.error("用户购买配额查询异常 cmd:{}", e);
            throw ResultCode.QUOTA_LACKING.newException("用户购买配额查询异常", e);
        }

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
        cmd.buildUniqueKey(UserTagTypeEnum.quota, context.getBizType(), context.getMemberOrder().getTradeId());
        cmd.setOpType(UserTagOpTypeEnum.ADD);

        QuotaExtensionContext quotaExtensionContext = new QuotaExtensionContext();
        quotaExtensionContext.setUserId(userId);
        quotaExtensionContext.setBizType(context.getBizType());
        quotaExtensionContext.setSkus(skus);

        extensionManager.getExtension(BizScene.of(context.getBizType()),
                QuotaExtension.class).buildUserTagOp(quotaExtensionContext);
        List<UserTagOpDO> usertagOps = quotaExtensionContext.getUserTagOpDOList();

        if (CollectionUtils.isEmpty(usertagOps)) {
            return;
        }
        cmd.setTags(usertagOps);
        long expireSeconds = usertagOps.stream()
                .max(Comparator.comparingLong(UserTagOpDO::getExpireSeconds)).get().getExpireSeconds();
        cmd.setExpireSeconds(expireSeconds);

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

}