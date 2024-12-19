/**
 * @(#)TestStarter.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.PeriodUtils;
import com.memberclub.common.util.TimeRange;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.common.SceneEnum;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.sdk.service.PerformService;
import com.memberclub.starter.AppStarter;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author 掘金五阳
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {AppStarter.class})
public class TestStarter {

    @Autowired
    private MemberPerformHisDao memberPerformHisDao;

    @Test
    public void test() {
        System.out.println("启动 ok");
        memberPerformHisDao.selectByUserId(1000);
        MemberPerformHis his = new MemberPerformHis();
        his.setBizType(1);
        his.setUserId(1000);

        TimeRange timeRange = PeriodUtils.buildTimeRangeFromBaseTime(2);


        his.setEtime(timeRange.getEtime());
        his.setStime(timeRange.getStime());
        his.setSkuId(10);
        his.setOrderId("1001");
        his.setOrderSystemType(1);
        his.setBuyCount(1);
        his.setTradeId("1_1001");


        his.setExtra("{}");
        his.setCtime(TimeUtil.now());
        his.setUtime(TimeUtil.now());

        int count = memberPerformHisDao.insert(his);
        System.out.println("插入数量" + count);
        List<MemberPerformHis> hisLists = memberPerformHisDao.selectByUserId(1000);
        System.out.println(hisLists);
    }

    @Test
    public void testJson() {
        MemberPerformHis his = new MemberPerformHis();
        his.setBizType(1);
        his.setUserId(1000);

        TimeRange timeRange = PeriodUtils.buildTimeRangeFromBaseTime(2);


        his.setEtime(timeRange.getEtime());
        his.setStime(timeRange.getStime());
        his.setSkuId(10);
        his.setOrderId("1001");
        his.setOrderSystemType(1);
        his.setTradeId("1_1001");
        his.setExtra("{}");
        his.setCtime(TimeUtil.now());
        his.setUtime(TimeUtil.now());

        String json = JsonUtils.toJson(his);
        System.out.println(json);
        MemberPerformHis performHis = JsonUtils.fromJson(json, MemberPerformHis.class);
        System.out.println(performHis);
    }

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;

    @Test
    public void testItem() {
        int cnt = 3;
        List<MemberPerformItem> items = Lists.newArrayList();
        for (int i = cnt; i > 0; i--) {
            MemberPerformItem item = new MemberPerformItem();
            item.setAssetCount(4);
            item.setBatchCode("2323");
            item.setBizType(1);
            item.setBuyIndex(1);
            item.setCtime(TimeUtil.now());
            item.setCycle(1);
            item.setPhase(1);
            item.setRightId(1);
            item.setRightType(1);
            item.setSkuId(1);
            item.setGrantType(1);
            item.setStatus(1);
            item.setTradeId("gjeigejoig" + i);
            item.setStime(TimeUtil.now());
            item.setEtime(TimeUtil.now());
            item.setUserId(1);
            item.setUtime(TimeUtil.now());
            items.add(item);
        }

        int num = memberPerformItemDao.insertIgnoreBatch(items);
        System.out.println(num);

        List<MemberPerformItem> itemsDb = memberPerformItemDao.selectByMap(ImmutableMap.of("user_id", 1));
        System.out.println(JsonUtils.toJson(itemsDb));
    }

    @Autowired
    private MemberOrderDao memberOrderDao;

    @Autowired
    private PerformService performService;

    @Test
    public void testDefaultMember() {
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setUserId(212);
        memberOrder.setOrderId("3232323");
        memberOrder.setOrderSystemType(1);
        memberOrder.setOriginPriceFen("3000");
        memberOrder.setActPriceFen("100");
        memberOrder.setBizType(1);
        memberOrder.setCtime(TimeUtil.now());
        memberOrder.setExtra("{}");
        memberOrder.setStatus(MemberOrderStatusEnum.PAYED.toInt());
        memberOrder.setTradeId(String.format("%s_%s", memberOrder.getOrderSystemType(), memberOrder.getOrderId()));
        memberOrder.setUserInfo("{}");
        List<SkuBuyDetailDO> skuBuyDetailDOS = Lists.newArrayList();
        SkuBuyDetailDO skuBuyDetailDO = new SkuBuyDetailDO();
        skuBuyDetailDOS.add(skuBuyDetailDO);
        skuBuyDetailDO.setBuyCount(1);
        skuBuyDetailDO.setSkuId(439434);
        MemberSkuSnapshotDO snapshotDO = new MemberSkuSnapshotDO();
        skuBuyDetailDO.setSkuSnapshot(snapshotDO);
        SkuPerformConfigDO skuPerformConfigDO = new SkuPerformConfigDO();
        snapshotDO.setPerformConfig(skuPerformConfigDO);
        SkuPerformItemConfigDO skuPerformItemConfigDO = new SkuPerformItemConfigDO();
        skuPerformItemConfigDO.setAssetCount(4);
        skuPerformItemConfigDO.setBizType(1);
        skuPerformItemConfigDO.setCycle(1);
        skuPerformItemConfigDO.setPeriodType(PeriodTypeEnum.FIX_DAY.toInt());
        skuPerformItemConfigDO.setRightId(32424);
        skuPerformItemConfigDO.setRightType(Integer.valueOf(SceneEnum.RIGHT_TYPE_SCENE_COUPON.getValue()));

        skuPerformConfigDO.setConfigs(ImmutableList.of(skuPerformItemConfigDO));
        snapshotDO.setPerformConfig(skuPerformConfigDO);

        memberOrder.setSkuDetails(JsonUtils.toJson(skuBuyDetailDOS));
        memberOrderDao.insert(memberOrder);
        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = new PerformCmd();
        cmd.setOrderId(memberOrder.getOrderId());
        cmd.setActPriceFen(memberOrder.getActPriceFen());
        cmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        cmd.setOrderSystemType(OrderSystemTypeEnum.COMMON_ORDER);
        cmd.setOriginPriceFen(memberOrder.getOriginPriceFen());
        cmd.setUserId(memberOrder.getUserId());

        performService.perform(cmd);
    }

}