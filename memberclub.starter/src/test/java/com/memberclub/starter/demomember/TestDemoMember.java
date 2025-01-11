/**
 * @(#)TestDemoMember.java, 十二月 21, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.demomember;

import com.google.common.collect.ImmutableMap;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.EncrptUtils;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.PeriodUtils;
import com.memberclub.common.util.TimeRange;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.common.OrderSystemTypeEnum;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyCmd;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyResponse;
import com.memberclub.domain.context.aftersale.contant.AftersaleSourceEnum;
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.contant.RefundTypeEnum;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewResponse;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformResp;
import com.memberclub.domain.context.perform.common.PerformItemStatusEnum;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.purchase.PurchaseSubmitResponse;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.aftersale.AftersaleOrderStatusEnum;
import com.memberclub.domain.dataobject.order.LocationInfo;
import com.memberclub.domain.dataobject.order.MemberOrderExtraInfo;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.dataobject.task.TaskContentDO;
import com.memberclub.domain.dataobject.task.perform.PerformTaskContentDO;
import com.memberclub.domain.entity.AftersaleOrder;
import com.memberclub.domain.entity.MemberOrder;
import com.memberclub.domain.entity.MemberPerformItem;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.entity.OnceTask;
import com.memberclub.domain.facade.AssetDO;
import com.memberclub.domain.facade.AssetStatusEnum;
import com.memberclub.infrastructure.mapstruct.AftersaleConvertor;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.infrastructure.mapstruct.PerformCustomConvertor;
import com.memberclub.infrastructure.mybatis.mappers.AftersaleOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.infrastructure.mybatis.mappers.OnceTaskDao;
import com.memberclub.sdk.aftersale.service.AftersaleBizService;
import com.memberclub.sdk.perform.service.PerformBizService;
import lombok.SneakyThrows;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author: 掘金五阳
 */
public class TestDemoMember extends TestDemoMemberPurchase {

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    @Autowired
    private AftersaleOrderDao aftersaleOrderDao;

    @Autowired
    private AftersaleBizService aftersaleBizService;

    @Autowired
    private OnceTaskDao onceTaskDao;

    @SneakyThrows
    @Test
    public void testDefaultMemberAndRetry() {
        PurchaseSubmitResponse response = submit(doubleRightsSku, 1);
        MemberOrderDO memberOrder = response.getMemberOrderDO();

        Mockito.reset(couponGrantFacade);

        Mockito.doThrow(new RuntimeException("mock error"))
                .when(couponGrantFacade).grant(Mockito.any());

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        try {
            PerformResp resp = performBizService.perform(cmd);
            Assert.assertTrue(resp.isSuccess());
            verifyData(cmd);
        } catch (Exception e) {
            CommonLog.error("首次调用出现异常", e);
        }

        Mockito.doCallRealMethod()
                .when(couponGrantFacade).grant(Mockito.any());
        Thread.sleep(1500);
        verifyData(cmd);
    }


    @SneakyThrows
    @Test
    public void testDefaultMemberAndMutilPeriodCardAndPeriodPerform() {
        PurchaseSubmitResponse response = submit(cycle3Sku, 1);
        MemberOrderDO memberOrder = response.getMemberOrderDO();


        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, 1);

        verifyTaskData(cmd, 4);

        List<OnceTask> tasks = onceTaskDao.queryTasksByUserId(cmd.getUserId());

        List<OnceTaskDO> taskDOS = tasks.stream().map(task -> {
            OnceTaskDO taskDO = PerformConvertor.INSTANCE.toOnceTaskDO(task);
            TaskContentDO contentDO = PerformCustomConvertor.toTaskContentDO(task.getContent(), task.getTaskContentClassName());
            taskDO.setTradeId(((PerformTaskContentDO) contentDO).getTradeId());
            taskDO.setContent(contentDO);
            return taskDO;
        }).collect(Collectors.toList());
        for (OnceTaskDO taskDO : taskDOS) {
            performBizService.periodPerform(taskDO);
        }
        verifyData(cmd, 3);
        //Thread.sleep(1000000);
    }


    @SneakyThrows
    @Test
    public void testDefaultMemberAndMutilPeriodCard() {
        PurchaseSubmitResponse response = submit(cycle3Sku, 1);
        MemberOrderDO memberOrder = response.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, 1);

        verifyTaskData(cmd, 4);
        //Thread.sleep(1000000);
    }

    @SneakyThrows
    @Test
    public void testDefaultMemberAndMutilPeriodCardAndRefund() {
        PurchaseSubmitResponse response = submit(cycle3Sku, 1);
        MemberOrderDO memberOrder = response.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, 1);

        verifyTaskData(cmd, 4);


        AfterSalePreviewCmd previewCmd = new AfterSalePreviewCmd();
        previewCmd.setUserId(cmd.getUserId());
        previewCmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        previewCmd.setTradeId(cmd.getTradeId());
        previewCmd.setSource(AftersaleSourceEnum.User);
        previewCmd.setOperator(String.valueOf(cmd.getUserId()));
        previewCmd.setOrderId(cmd.getOrderId());
        previewCmd.setOrderSystemTypeEnum(cmd.getOrderSystemType());


        AfterSalePreviewResponse respose = aftersaleBizService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.ALL_REFUND, respose.getRefundType());

        /*******************部分使用,结果为部分退********/
        for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
            entry.getValue().get(0).setStatus(AssetStatusEnum.USED.getCode());
        }
        respose = aftersaleBizService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.PORTION_RFUND, respose.getRefundType());


        AftersaleApplyCmd applyCmd = new AftersaleApplyCmd();
        applyCmd = AftersaleConvertor.INSTANCE.toApplyCmd(previewCmd);
        applyCmd.setDigests(respose.getDigests());
        applyCmd.setReason("不想要了!");
        applyCmd.setDigestVersion(respose.getDigestVersion());
        //applyCmd.setDigestVersion(0);
        AftersaleApplyResponse aftersaleApplyResponse = aftersaleBizService.apply(applyCmd);
        //waitH2();

        verifyOrderRefund(applyCmd, false);
        Assert.assertTrue(aftersaleApplyResponse.isSuccess());


    }


    @SneakyThrows
    @Test
    public void testDefaultMemberAndBuyCount() {
        int buyCount = 3;
        PurchaseSubmitResponse response = submit(doubleRightsSku, buyCount);
        MemberOrderDO memberOrder = response.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, buyCount);

        //Thread.sleep(1000000);
    }


    @SneakyThrows
    @Test
    public void testDefaultMember() {
        PurchaseSubmitResponse response = submit(doubleRightsSku, 1);
        MemberOrderDO memberOrder = response.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd);

        //Thread.sleep(1000000);
    }


    @SneakyThrows
    @Test
    public void testDefaultMemberRefundAndApply() {
        PurchaseSubmitResponse submitResponse = submit(doubleRightsSku, 3);
        MemberOrderDO memberOrder = submitResponse.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, 3);

        AfterSalePreviewCmd previewCmd = new AfterSalePreviewCmd();
        previewCmd.setUserId(cmd.getUserId());
        previewCmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        previewCmd.setTradeId(cmd.getTradeId());
        previewCmd.setSource(AftersaleSourceEnum.User);
        previewCmd.setOperator(String.valueOf(cmd.getUserId()));
        previewCmd.setOrderId(cmd.getOrderId());
        previewCmd.setOrderSystemTypeEnum(cmd.getOrderSystemType());

        AfterSalePreviewResponse response = aftersaleBizService.preview(previewCmd);

        Assert.assertEquals(true, response.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.ALL_REFUND, response.getRefundType());

        AftersaleApplyCmd applyCmd = AftersaleConvertor.INSTANCE.toAftersaleApplyCmd(previewCmd);
        applyCmd.setDigests(response.getDigests());
        applyCmd.setDigestVersion(response.getDigestVersion());

        AftersaleApplyResponse applyResponse = aftersaleBizService.apply(applyCmd);
        waitH2();

        Assert.assertTrue(applyResponse.isSuccess());


        verifyOrderRefund(applyCmd, true);


    }

    private void verifyOrderRefund(AftersaleApplyCmd applyCmd, boolean completeRefund) {
        List<AftersaleOrder> orders = aftersaleOrderDao.queryByTradeId(applyCmd.getUserId(), applyCmd.getTradeId());
        Assert.assertEquals(1, orders.size());

        for (AftersaleOrder order : orders) {
            Assert.assertEquals(AftersaleOrderStatusEnum.AFTERSALE_SUCCESS.getCode(), order.getStatus());
        }

        List<MemberSubOrder> hisAfterApply = memberSubOrderDao.selectByTradeId(applyCmd.getUserId(), applyCmd.getTradeId());
        List<MemberPerformItem> itemsAfterApply = memberPerformItemDao.selectByTradeId(applyCmd.getUserId(), applyCmd.getTradeId());
        for (MemberSubOrder his : hisAfterApply) {
            Assert.assertEquals(SubOrderPerformStatusEnum.getReversedStatus(completeRefund),
                    his.getPerformStatus());
        }
        for (MemberPerformItem item : itemsAfterApply) {
            Assert.assertEquals(PerformItemStatusEnum.getReversedStatus(completeRefund),
                    item.getStatus());
            for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
                if (StringUtils.equals(item.getBatchCode(), entry.getKey())) {
                    boolean hasReverse = entry.getValue().stream().anyMatch(v ->
                            v.getStatus() == AssetStatusEnum.FREEZE.getCode());
                    Assert.assertTrue(hasReverse);
                }
            }
        }
        for (MemberSubOrder memberSubOrder : hisAfterApply) {
            List<OnceTask> tasks =
                    onceTaskDao.queryTasksByUserIdAndGroupId(memberSubOrder.getUserId(), String.valueOf(memberSubOrder.getSubTradeId()));

            for (OnceTask task : tasks) {
                if (task.getStatus() == OnceTaskStatusEnum.CANCEL.getCode() ||
                        task.getStatus() == OnceTaskStatusEnum.SUCC.getCode()) {

                } else {
                    Assert.fail("任务状态异常:" + task.getStatus());
                }
            }
        }

    }


    @SneakyThrows
    @Test
    public void testDefaultMemberRefund() {
        PurchaseSubmitResponse submitResponse = submit(doubleRightsSku, 3);
        MemberOrderDO memberOrder = submitResponse.getMemberOrderDO();

        MemberOrder orderInDb = memberOrderDao.selectByTradeId(memberOrder.getUserId(), memberOrder.getTradeId());
        System.out.println(JsonUtils.toJson(orderInDb));

        PerformCmd cmd = buildCmd(memberOrder);

        PerformResp resp = performBizService.perform(cmd);
        Assert.assertTrue(resp.isSuccess());
        verifyData(cmd, 3);

        AfterSalePreviewCmd previewCmd = new AfterSalePreviewCmd();
        previewCmd.setUserId(cmd.getUserId());
        previewCmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        previewCmd.setTradeId(cmd.getTradeId());
        previewCmd.setSource(AftersaleSourceEnum.User);
        previewCmd.setOperator(String.valueOf(cmd.getUserId()));
        previewCmd.setOrderId(cmd.getOrderId());
        previewCmd.setOrderSystemTypeEnum(cmd.getOrderSystemType());


        AfterSalePreviewResponse respose = aftersaleBizService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.ALL_REFUND, respose.getRefundType());

        /*******************部分使用,结果为部分退********/
        for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
            entry.getValue().get(0).setStatus(AssetStatusEnum.USED.getCode());
        }
        respose = aftersaleBizService.preview(previewCmd);
        Assert.assertEquals(true, respose.isAftersaleEnabled());
        Assert.assertEquals(RefundTypeEnum.PORTION_RFUND, respose.getRefundType());


        AftersaleApplyCmd applyCmd = new AftersaleApplyCmd();
        applyCmd = AftersaleConvertor.INSTANCE.toApplyCmd(previewCmd);
        applyCmd.setDigests(respose.getDigests());
        applyCmd.setReason("不想要了!");
        applyCmd.setDigestVersion(respose.getDigestVersion());
        //applyCmd.setDigestVersion(0);
        AftersaleApplyResponse aftersaleApplyResponse = aftersaleBizService.apply(applyCmd);
        //waitH2();

        verifyOrderRefund(applyCmd, false);
        Assert.assertTrue(aftersaleApplyResponse.isSuccess());

        /*******************已用尽,结果为不可退********/

        for (Map.Entry<String, List<AssetDO>> entry : couponGrantFacade.assetBatchCode2Assets.entrySet()) {
            for (AssetDO assetDO : entry.getValue()) {
                assetDO.setStatus(AssetStatusEnum.USED.getCode());
            }
        }
        respose = aftersaleBizService.preview(previewCmd);
        Assert.assertEquals(false, respose.isAftersaleEnabled());
        Assert.assertEquals(AftersaleUnableCode.USE_OUT_ERROR.getCode(), respose.getUnableCode());
        //Thread.sleep(10000000);
    }


    private void verifyData(PerformCmd cmd) {
        verifyData(cmd, 1);
    }

    private void verifyData(PerformCmd cmd, int buyCount) {
        List<MemberSubOrder> subOrders = memberSubOrderDao.selectByUserId(cmd.getUserId());
        for (MemberSubOrder memberSubOrder : subOrders) {
            Assert.assertEquals(SubOrderPerformStatusEnum.PERFORM_SUCC.getCode(), memberSubOrder.getPerformStatus());
        }
        Assert.assertEquals(1, subOrders.size());
        List<MemberPerformItem> items = memberPerformItemDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());

        for (MemberPerformItem item : items) {
            Assert.assertEquals(PerformItemStatusEnum.PERFORM_SUCCESS.getCode(), item.getStatus());
        }
        Assert.assertEquals(buyCount * 2, items.size());

        MemberOrder orderFromDb = memberOrderDao.selectByTradeId(cmd.getUserId(), cmd.getTradeId());
        Assert.assertEquals(MemberOrderStatusEnum.PERFORMED.getCode(), orderFromDb.getStatus());

        System.out.println(JsonUtils.toJson(subOrders));
    }

    private void verifyTaskData(PerformCmd cmd, int taskSize) {
        List<OnceTask> tasks = onceTaskDao.queryTasksByUserId(cmd.getUserId());
        Assert.assertEquals(taskSize, tasks.size());

        for (OnceTask task : tasks) {
            Assert.assertNotNull(task.getTaskGroupId());
        }
    }


    @SneakyThrows
    private PerformCmd buildCmd(MemberOrderDO memberOrder) {
        PerformCmd cmd = new PerformCmd();
        cmd.setOrderId(memberOrder.getOrderInfo().getOrderId());
        cmd.setActPriceFen(memberOrder.getActPriceFen());
        cmd.setBizType(BizTypeEnum.DEMO_MEMBER);
        cmd.setOrderSystemType(OrderSystemTypeEnum.COMMON_ORDER);
        cmd.setOriginPriceFen(memberOrder.getOriginPriceFen());
        cmd.setUserId(memberOrder.getUserId());
        cmd.setTradeId(memberOrder.getTradeId());
        return cmd;
    }


    private MemberOrder buildMemberOrder() {
        return buildMemberOrder(1, 1);
    }


    @SneakyThrows
    private MemberOrder buildMemberOrder(int buyCount, int cycle) {
        MemberOrder memberOrder = new MemberOrder();
        memberOrder.setUserId(userIdGenerator.incrementAndGet());
        memberOrder.setOrderId(orderIdGenerator.incrementAndGet() + "");
        memberOrder.setOrderSystemType(1);
        memberOrder.setOriginPriceFen(3000);
        memberOrder.setActPriceFen(699);
        memberOrder.setBizType(1);
        memberOrder.setCtime(TimeUtil.now());
        memberOrder.setExtra("{}");
        memberOrder.setStatus(MemberOrderStatusEnum.PAYED.getCode());
        memberOrder.setTradeId(String.format("%s_%s", memberOrder.getOrderSystemType(), memberOrder.getOrderId()));

        List<SkuInfoDO> skuInfoDOS = Lists.newArrayList();


        CommonUserInfo userInfo = new CommonUserInfo();
        userInfo.setUuid(RandomStringUtils.randomAlphabetic(12));
        userInfo.setPhone(RandomStringUtils.randomNumeric(11));
        userInfo.setMaskedPhone(EncrptUtils.generatePhoneMask(userInfo.getPhone()));


        String key = EncrptUtils.generateAESKey();

        userInfo.setKey(key);
        userInfo.setEncryptedPhone(EncrptUtils.encryptAES(userInfo.getPhone(), key));

        LocationInfo locationInfo = new LocationInfo();
        locationInfo.setActualLatitude("8493458355");
        locationInfo.setActualLongitude("48934834");
        locationInfo.setActualSecondCityId("110100");
        locationInfo.setActualThirdCityId("4384394");

        MemberOrderExtraInfo extraInfo = new MemberOrderExtraInfo();
        extraInfo.setUserInfo(userInfo);
        extraInfo.setLocationInfo(locationInfo);

        memberOrder.setExtra(JsonUtils.toJson(extraInfo));

        System.out.println("解密后的电话号码： " + EncrptUtils.decrypt(userInfo.getEncryptedPhone(), userInfo.getKey()));

        //memberOrder.setSkuDetails(JsonUtils.toJson(skuInfoDOS));
        return memberOrder;
    }

    @Test
    public void test() {
        System.out.println("启动 ok");
        memberSubOrderDao.selectByUserId(1000);
        MemberSubOrder his = new MemberSubOrder();
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

        int count = memberSubOrderDao.insert(his);
        System.out.println("插入数量" + count);
        List<MemberSubOrder> hisLists = memberSubOrderDao.selectByUserId(1000);
        System.out.println(hisLists);
    }

    @Test
    public void testJson() {
        MemberSubOrder his = new MemberSubOrder();
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
        MemberSubOrder performHis = JsonUtils.fromJson(json, MemberSubOrder.class);
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
    private PerformBizService performBizService;

    /*@SpyBean(name = "couponGrantFacade", value = MockCouponGrantFacade.class)
    private CouponGrantFacade mockCouponGrantFacade;*/
}