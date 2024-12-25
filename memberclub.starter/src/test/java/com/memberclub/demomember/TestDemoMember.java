/**
 * @(#)TestDemoMember.java, 十二月 21, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.demomember;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.PeriodUtils;
import com.memberclub.common.util.TimeRange;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.MemberOrderStatusEnum;
import com.memberclub.domain.common.MemberPerformHisStatusEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.common.PeriodTypeEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyCmd;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyResponse;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewResponse;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformResp;
import com.memberclub.domain.dataobject.perform.SkuBuyDetailDO;
import com.memberclub.domain.dataobject.sku.MemberSkuSnapshotDO;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.facade.AssetDO;
import com.memberclub.infrastruct.facade.impl.MockAssetsFacade;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.mock.MockBaseTest;
import com.memberclub.sdk.service.aftersale.AftersaleService;
import com.memberclub.sdk.service.perform.PerformService;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.SpyBean;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * author: 掘金五阳
 */
public class TestDemoMember extends MockBaseTest {

    @Autowired
    private MemberPerformHisDao memberPerformHisDao;

    @SpyBean
    private MockAssetsFacade couponGrantFacade;

    @Autowired
    private AftersaleService aftersaleService;


    @Test
    public void testDefaultMember() {
        MemberOrder memberOrder = buildMemberOrder();
        memberOrderDao.insert(memberOrder);

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd);
    }

    @Test
    public void testDefaultMemberRefund() {
        MemberOrder memberOrder = buildMemberOrder();
        memberOrderDao.insert(memberOrder);

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd);

        AfterSalePreviewCmd previewCmd = new AfterSalePreviewCmd();
        previewCmd.setUserId(cmd.getUserId());
        previewCmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        previewCmd.setTradeId(cmd.getTradeId());
        previewCmd.setSource(AftersaleSourceEnum.User);
        previewCmd.setOperator(String.valueOf(cmd.getUserId()));
        previewCmd.setOrderId(cmd.getOrderId());
        previewCmd.setOrderSystemTypeEnum(cmd.getOrderSystemType());


        AfterSalePreviewResponse respose = aftersaleService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.ALL_REFUND, respose.getRefundType());

        /*******************部分使用,结果为部分退********/
        for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
            entry.getValue().get(0).setStatus(1);
        }
        respose = aftersaleService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.PORTION_RFUND, respose.getRefundType());


        AftersaleApplyCmd applyCmd = new AftersaleApplyCmd();
        applyCmd = PerformConvertor.INSTANCE.toApplyCmd(previewCmd);
        applyCmd.setDigests(respose.getDigests());
        applyCmd.setDigestVersion(respose.getDigestVersion());
        //applyCmd.setDigestVersion(0);
        AftersaleApplyResponse aftersaleApplyResponse = aftersaleService.apply(applyCmd);
        Assert.assertTrue(aftersaleApplyResponse.isSuccess());

        /*******************已用尽,结果为不可退********/

        for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
            for (AssetDO assetDO : entry.getValue()) {
                assetDO.setStatus(1);
            }
        }
        respose = aftersaleService.preview(previewCmd);
        Assert.assertEquals(false, respose.isAftersaleEnabled());
        Assert.assertEquals(AftersaleUnableCode.USE_OUT_ERROR.toInt(), respose.getUnableCode());
    }

    private void verifyData(PerformCmd cmd) {
        List<MemberPerformHis> hisList = memberPerformHisDao.selectByUserId(cmd.getUserId());
        for (MemberPerformHis memberPerformHis : hisList) {
            Assert.assertEquals(MemberPerformHisStatusEnum.PERFORM_SUCC.toInt(), memberPerformHis.getStatus());
        }
        List<MemberPerformItem> items = memberPerformItemDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        for (MemberPerformItem item : items) {
            Assert.assertEquals(PerformItemStatusEnum.PERFORM_SUCC.toInt(), item.getStatus());
        }

        MemberOrder orderFromDb = memberOrderDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        Assert.assertEquals(MemberOrderStatusEnum.PERFORMED.toInt(), orderFromDb.getStatus());

        System.out.println(JsonUtils.toJson(hisList));
    }


    private PerformCmd buildCmd(MemberOrder memberOrder) {
        PerformCmd cmd = new PerformCmd();
        cmd.setOrderId(memberOrder.getOrderId());
        cmd.setActPriceFen(memberOrder.getActPriceFen());
        cmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        cmd.setOrderSystemType(OrderSystemTypeEnum.COMMON_ORDER);
        cmd.setOriginPriceFen(memberOrder.getOriginPriceFen());
        cmd.setUserId(memberOrder.getUserId());
        cmd.setTradeId(String.format("%s_%s", cmd.getOrderSystemType().toInt(), cmd.getOrderId()));
        return cmd;
    }


    public AtomicLong userIdGenerator = new AtomicLong(RandomUtils.nextInt());

    public AtomicLong orderIdGenerator = new AtomicLong(System.currentTimeMillis());

    private MemberOrder buildMemberOrder() {
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setUserId(userIdGenerator.incrementAndGet());
        memberOrder.setOrderId(orderIdGenerator.incrementAndGet() + "");
        memberOrder.setOrderSystemType(1);
        memberOrder.setOriginPriceFen("3000");
        memberOrder.setActPriceFen("699");
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
        skuPerformItemConfigDO.setPeriodCount(31);
        skuPerformItemConfigDO.setRightType(1);

        skuPerformConfigDO.setConfigs(ImmutableList.of(skuPerformItemConfigDO));
        snapshotDO.setPerformConfig(skuPerformConfigDO);

        memberOrder.setSkuDetails(JsonUtils.toJson(skuBuyDetailDOS));
        return memberOrder;
    }

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

    /*@SpyBean(name = "couponGrantFacade", value = MockCouponGrantFacade.class)
    private CouponGrantFacade mockCouponGrantFacade;*/
}