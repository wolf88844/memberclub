/**
 * @(#)DefaultGenerateAfterSalePlanDigestExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview.impl;

import com.google.common.base.Charsets;
import com.google.common.collect.Lists;
import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.sdk.extension.aftersale.preview.GenerateAfterSalePlanDigestExtension;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;

import java.security.MessageDigest;
import java.util.Base64;
import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认的售后计划摘要生成", bizScenes = {
        //@Route(bizType = BizTypeEnum.DEFAULT, scenes = {SceneEnum.DEFAULT_SCENE})
        @Route(bizType = BizTypeEnum.DEMO_MEMBER, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultGenerateAfterSalePlanDigestExtension implements GenerateAfterSalePlanDigestExtension {

    @SneakyThrows
    @Override
    public void generateDigest(AftersalePreviewContext context) {
        List<Object> keys = Lists.newArrayList();
        keys.add(context.getCmd().getTradeId());
        keys.add(context.getRecommendRefundPrice());
        keys.add(context.getRefundType().toInt());
        keys.add(context.getRefundWay().toInt());

        String value = StringUtils.join(keys, ",");


        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        String digest = Base64.getUrlEncoder().encodeToString(
                messageDigest.digest(value.getBytes(Charsets.UTF_8)));
        context.setDigests(digest);
        context.setDigestVersion(1);
        CommonLog.info("生成售后计划摘要 版本:{},{}", context.getDigestVersion(), context.getDigests());

        return;
    }
}