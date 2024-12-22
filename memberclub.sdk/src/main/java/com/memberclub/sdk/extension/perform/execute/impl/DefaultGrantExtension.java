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
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.dataobject.perform.execute.ItemGrantResult;
import com.memberclub.domain.dataobject.perform.execute.ItemGroupGrantResult;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.domain.facade.AssetDO;
import com.memberclub.domain.facade.GrantItemDO;
import com.memberclub.domain.facade.GrantRequestDO;
import com.memberclub.domain.facade.GrantResponseDO;
import com.memberclub.infrastructure.facade.AssetsFacade;
import com.memberclub.sdk.extension.perform.execute.PerformItemGrantExtension;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "券类型默认发放扩展点实现", bizScenes =
        {@Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = SceneEnum.RIGHT_TYPE_SCENE_COUPON)})
public class DefaultGrantExtension implements PerformItemGrantExtension {

    //@Qualifier("couponGrantFacade")
    @Resource()
    private AssetsFacade assetsFacade;

    @Override
    public ItemGroupGrantResult grant(PerformItemContext context, List<PerformItemDO> items) {

        GrantRequestDO request = new GrantRequestDO();
        request.setUserId(context.getPerformContext().getUserId());

        List<GrantItemDO> grantItemDOS = Lists.newArrayList();
        for (PerformItemDO item : items) {
            GrantItemDO grantItemDO = new GrantItemDO();
            grantItemDO.setItemToken(item.getItemToken());
            grantItemDO.setAssetCount(item.getAssetCount());
            grantItemDO.setChannelKey(String.valueOf(item.getRightId()));
            grantItemDO.setStime(item.getStime());
            grantItemDO.setEtime(item.getEtime());
            grantItemDO.setRightType(item.getRightType());
            grantItemDOS.add(grantItemDO);
        }

        request.setGrantItems(grantItemDOS);
        GrantResponseDO response = assetsFacade.grant(request);
        if (!response.isSuccess()) {
            ResultCode.DEPENDENCY_ERROR.throwException();
        }
        CommonLog.warn("调用发券结果:{}, 入参:{}", response, request);
        if (CollectionUtils.isEmpty(response.getItemToken2AssetsMap())) {
            ResultCode.DEPENDENCY_ERROR.throwException("下游发券列表为空");
        }

        Map<String, ItemGrantResult> grantMap = Maps.newHashMap();
        for (Map.Entry<String, List<AssetDO>> entry : response.getItemToken2AssetsMap().entrySet()) {
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