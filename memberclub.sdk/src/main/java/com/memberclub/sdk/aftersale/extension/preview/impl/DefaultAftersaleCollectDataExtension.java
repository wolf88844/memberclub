/**
 * @(#)DefaultAftersaleCollectDataExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.extension.preview.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionImpl;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.aftersale.extension.preview.AftersaleCollectDataExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * author: 掘金五阳
 */
@ExtensionImpl(desc = "默认售后数据收集扩展点", bizScenes = {
        @Route(bizType = BizTypeEnum.DEFAULT, scenes = {SceneEnum.DEFAULT_SCENE})
})
public class DefaultAftersaleCollectDataExtension implements AftersaleCollectDataExtension {

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private MemberPerformHisDao memberPerformHisDao;

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Override
    public AftersalePreviewContext collect(AfterSalePreviewCmd cmd) {
        AftersalePreviewContext context = new AftersalePreviewContext();
        MemberOrder memberOrder = memberOrderDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        context.setMemberOrder(memberOrder);

        List<MemberPerformHis> performHisList = memberPerformHisDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        context.setPerformHisList(performHisList);

        List<MemberPerformItem> items = memberPerformItemDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        context.setPerformItems(items);
        context.setPayPriceFen(Integer.valueOf(memberOrder.getActPriceFen()));
        context.setDigestVersion(cmd.getDigestVersion());

        return context;
    }
}