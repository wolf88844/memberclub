/**
 * @(#)CouponDefaultGrantExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform.execute.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.exception.ResultCode;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.execute.ItemGrantResult;
import com.memberclub.domain.dataobject.perform.execute.ItemGroupGrantResult;
import com.memberclub.infrastructure.facade.CouponGrantFacade;
import com.memberclub.infrastructure.facade.data.CouponDO;
import com.memberclub.infrastructure.facade.data.CouponGrantItemDO;
import com.memberclub.infrastructure.facade.data.CouponGrantRequestDO;
import com.memberclub.infrastructure.facade.data.CouponGrantResponseDO;
import com.memberclub.sdk.extension.perform.execute.PerformItemGrantExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "券类型默认发放扩展点实现", bizScenes =
        {@Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.RIGHT_TYPE_SCENE_COUPON)})
public class CouponDefaultGrantExtension implements PerformItemGrantExtension {

    @Autowired
    private CouponGrantFacade couponGrantFacade;

    @Override
    public ItemGroupGrantResult grant(PerformItemContext context, List<PerformItemDO> items) {

        CouponGrantRequestDO request = new CouponGrantRequestDO();
        request.setUserId(context.getPerformContext().getUserId());

        List<CouponGrantItemDO> grantItemDOS = Lists.newArrayList();
        for (PerformItemDO item : items) {
            CouponGrantItemDO couponGrantItemDO = new CouponGrantItemDO();
            couponGrantItemDO.setItemToken(item.getItemToken());
            couponGrantItemDO.setAssetCount(item.getAssetCount());
            couponGrantItemDO.setChannelKey(String.valueOf(item.getRightId()));
            couponGrantItemDO.setStime(item.getStime());
            couponGrantItemDO.setEtime(item.getEtime());
            grantItemDOS.add(couponGrantItemDO);
        }

        request.setGrantItems(grantItemDOS);
        CouponGrantResponseDO response = couponGrantFacade.grant(request);
        if (!response.isSuccess()) {
            ResultCode.DEPENDENCY_GRANT_ERROR.throwException();
        }
        CommonLog.warn("调用发券结果:{}, 入参:{}", response, request);
        if (CollectionUtils.isEmpty(response.getItemToken2CouponMap())) {
            ResultCode.DEPENDENCY_GRANT_ERROR.throwException("下游发券列表为空");
        }

        Map<String, ItemGrantResult> grantMap = Maps.newHashMap();
        for (Map.Entry<String, List<CouponDO>> entry : response.getItemToken2CouponMap().entrySet()) {
            String itemToken = entry.getKey();
            String batchCode = entry.getValue().get(0).getBatchCode();

            ItemGrantResult result = new ItemGrantResult();
            result.setBatchCode(batchCode);
            grantMap.put(itemToken, result);
        }

        ItemGroupGrantResult result = new ItemGroupGrantResult();
        result.setGrantMap(grantMap);
        return result;
    }
}