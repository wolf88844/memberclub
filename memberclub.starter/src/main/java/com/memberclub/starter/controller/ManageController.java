/**
 * @(#)ManageController.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.controller;

import com.google.common.collect.Lists;
import com.memberclub.domain.context.oncetask.trigger.OnceTaskTriggerCmd;
import com.memberclub.domain.context.perform.PerformCmd;
import com.memberclub.domain.context.perform.PerformResp;
import com.memberclub.domain.context.purchase.PurchaseSkuSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitResponse;
import com.memberclub.domain.context.purchase.common.PurchaseSourceEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.aftersale.ClientInfo;
import com.memberclub.domain.dataobject.order.LocationInfo;
import com.memberclub.domain.dataobject.purchase.MemberOrderDO;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.sdk.memberorder.domain.MemberOrderDomainService;
import com.memberclub.sdk.perform.service.PerformBizService;
import com.memberclub.sdk.purchase.service.biz.PurchaseBizService;
import com.memberclub.sdk.sku.service.SkuDomainService;
import com.memberclub.starter.controller.convertor.PurchaseConvertor;
import com.memberclub.starter.controller.vo.PurchaseSubmitVO;
import com.memberclub.starter.controller.vo.TestPayRequest;
import com.memberclub.starter.controller.vo.test.PurchaseTestRequest;
import com.memberclub.starter.job.OnceTaskTriggerBizService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 测试使用
 * author: 掘金五阳
 */
@Profile({"ut", "standalone", "test"})
@RestController("/manage")
@Api(value = "管理接口", tags = {"商品列表"})
public class ManageController {

    public static final Logger LOG = LoggerFactory.getLogger(ManageController.class);

    @Autowired
    private PurchaseBizService purchaseBizService;


    @Autowired
    private SkuDomainService skuDomainService;

    @ApiOperation("查询商品列表")
    @PostMapping("/sku/list")
    @ResponseBody
    public List<SkuInfoDO> listSkus() {
        return skuDomainService.queryAllSkus();
    }


    @PostMapping("/purchase/submit")
    public PurchaseSubmitResponse submit(@RequestBody PurchaseTestRequest request) {
        try {
            PurchaseSubmitVO param = new PurchaseSubmitVO();
            ClientInfo clientInfo = new ClientInfo();
            clientInfo.setClientCode(1);
            clientInfo.setClientName("member-ios");
            param.setClientInfo(clientInfo);
            CommonUserInfo userInfo = new CommonUserInfo();
            userInfo.setIp("127.0.0.1");
            SkuInfoDO skuInfo = skuDomainService.queryById(request.getSkuId());

            param.setBizType(skuInfo.getBizType());
            param.setUserInfo(userInfo);
            param.setSubmitSource(PurchaseSourceEnum.HOMEPAGE.getCode());
            PurchaseSubmitCmd cmd = PurchaseConvertor.toSubmitCmd(param);

            PurchaseSkuSubmitCmd skuCmd = new PurchaseSkuSubmitCmd();
            if (request.getBuyCount() > 10) {
                throw new RuntimeException("数量太多");
            }
            skuCmd.setBuyCount(request.getBuyCount());
            skuCmd.setSkuId(request.getSkuId());
            cmd.setSkus(Lists.newArrayList(skuCmd));
            cmd.setUserId(RandomUtils.nextLong(1, 1000000));
            cmd.setSubmitToken(RandomStringUtils.randomAscii(10));

            LocationInfo locationInfo = new LocationInfo();
            locationInfo.setActualSecondCityId("110100");
            cmd.setLocationInfo(locationInfo);

            PurchaseSubmitResponse response = purchaseBizService.submit(cmd);
            return response;
        } catch (Exception e) {
            LOG.info("提单失败:{}", request, e);
            PurchaseSubmitResponse response = new PurchaseSubmitResponse();
            response.setMsg(e.getMessage());
            response.setSuccess(false);
            return response;
        }
    }

    @Autowired
    private MemberOrderDomainService memberOrderDomainService;

    @Autowired
    private PerformBizService performBizService;

    @ResponseBody
    @PostMapping("/purchase/pay")
    public PerformResp pay(@RequestBody TestPayRequest request) {
        PerformCmd cmd = new PerformCmd();
        cmd.setUserId(request.getUserId());
        cmd.setTradeId(request.getTradeId());
        MemberOrderDO order = memberOrderDomainService.getMemberOrderDO(request.getUserId(),
                request.getTradeId());
        if (order == null) {
            throw new RuntimeException("输入错误订单 id");
        }
        cmd.setBizType(order.getBizType());
        cmd.setOrderSystemType(order.getOrderInfo().getOrderSystemType());
        cmd.setOrderId(order.getOrderInfo().getOrderId());
        return performBizService.perform(cmd);
    }

    @PostMapping("/purchase/submitAndPay")
    public PerformResp submitAndPay(@RequestBody PurchaseTestRequest request) {
        PurchaseSubmitResponse response = submit(request);
        if (response.isSuccess()) {
            TestPayRequest payRequest = new TestPayRequest();
            payRequest.setUserId(response.getMemberOrderDO().getUserId());
            payRequest.setTradeId(response.getMemberOrderDO().getTradeId());
            return pay(payRequest);
        }
        throw new RuntimeException("提单失败");
    }

    @Autowired
    private OnceTaskTriggerBizService onceTaskTriggerBizService;

    @PostMapping("/task/trigger")
    public void periodPerform(@RequestBody OnceTaskTriggerCmd cmd) {
        onceTaskTriggerBizService.triggerPeriodPerform(cmd);
    }

}