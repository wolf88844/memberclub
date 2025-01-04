/**
 * @(#)TestDemoMemberPurchase.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.demomember;

import com.google.common.collect.ImmutableList;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.context.purchase.PurchaseSkuSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.domain.context.purchase.common.PurchaseSourceEnum;
import com.memberclub.domain.dataobject.CommonUserInfo;
import com.memberclub.domain.dataobject.aftersale.ClientInfo;
import com.memberclub.domain.dataobject.order.LocationInfo;
import com.memberclub.domain.dataobject.perform.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.dataobject.sku.SkuSaleInfo;
import com.memberclub.domain.dataobject.sku.SkuSettleInfo;
import com.memberclub.domain.dataobject.sku.SkuViewInfo;
import com.memberclub.domain.dataobject.sku.rights.RightViewInfo;
import com.memberclub.sdk.purchase.service.biz.PurchaseBizService;
import com.memberclub.starter.mock.MockBaseTest;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
public class TestDemoMemberPurchase extends MockBaseTest {


    public SkuInfoDO doubleRightsSku = null;

    @Autowired
    public PurchaseBizService purchaseBizService;

    @Test
    public void testSubmit() {
        PurchaseSubmitCmd cmd = new PurchaseSubmitCmd();
        LocationInfo locationInfo = new LocationInfo();
        ClientInfo clientInfo = new ClientInfo();
        CommonUserInfo userInfo = new CommonUserInfo();

        locationInfo.setActualSecondCityId("110100");
        clientInfo.setClientCode(1);
        clientInfo.setClientName("ios");

        userInfo.setIp("127.0.0.1");

        cmd.setClientInfo(clientInfo);
        cmd.setUserInfo(userInfo);
        cmd.setLocationInfo(locationInfo);

        cmd.setUserId(userIdGenerator.incrementAndGet());
        cmd.setBizType(BizTypeEnum.DEMO_MEMBER);


        PurchaseSkuSubmitCmd sku = new PurchaseSkuSubmitCmd();
        sku.setSkuId(doubleRightsSku.getSkuId());
        sku.setBuyCount(1);
        cmd.setSkus(Lists.newArrayList(sku));

        cmd.setSource(PurchaseSourceEnum.HOMEPAGE);
        cmd.setSubmitToken(RandomStringUtils.randomAscii(10));

        purchaseBizService.submit(cmd);
    }


    @Before
    public void init() {
        doubleRightsSku = buildDoubleRightsSku(1);
        mockSkuBizService.addSku(doubleRightsSku.getSkuId(), doubleRightsSku);
    }


    public SkuInfoDO buildDoubleRightsSku(int cycle) {
        SkuInfoDO skuInfoDO = new SkuInfoDO();

        skuInfoDO.setSkuId(RandomUtils.nextInt());
        skuInfoDO.setBizType(BizTypeEnum.DEMO_MEMBER.getCode());

        SkuSaleInfo skuSaleInfo = new SkuSaleInfo();
        skuSaleInfo.setOriginPriceFen(3000);
        skuSaleInfo.setSalePriceFen(699);

        skuInfoDO.setSaleInfo(skuSaleInfo);

        SkuSettleInfo settleInfo = new SkuSettleInfo();
        settleInfo.setContractorId("438098434");
        settleInfo.setSettlePriceFen(300);

        skuInfoDO.setSettleInfo(settleInfo);

        SkuViewInfo viewInfo = new SkuViewInfo();
        viewInfo.setDisplayDesc("大额红包");
        viewInfo.setDisplayName("大额红包");
        viewInfo.setInternalDesc("大额红包 5 元");
        viewInfo.setInternalName("大额红包 5 元");
        skuInfoDO.setViewInfo(viewInfo);


        SkuPerformConfigDO skuPerformConfigDO = new SkuPerformConfigDO();
        skuInfoDO.setPerformConfig(skuPerformConfigDO);


        SkuPerformItemConfigDO skuPerformItemConfigDO = new SkuPerformItemConfigDO();
        skuPerformItemConfigDO.setAssetCount(4);
        skuPerformItemConfigDO.setBizType(1);
        skuPerformItemConfigDO.setCycle(cycle);
        skuPerformItemConfigDO.setPeriodType(PeriodTypeEnum.FIX_DAY.toInt());
        skuPerformItemConfigDO.setRightId(32424);
        skuPerformItemConfigDO.setPeriodCount(31);
        skuPerformItemConfigDO.setRightType(1);
        skuPerformItemConfigDO.setProviderId("1");
        RightViewInfo rightViewInfo = new RightViewInfo();
        rightViewInfo.setDisplayName("会员立减券权益");
        skuPerformItemConfigDO.setViewInfo(rightViewInfo);


        SkuPerformItemConfigDO skuPerformItemConfigDO2 = new SkuPerformItemConfigDO();
        skuPerformItemConfigDO2.setAssetCount(4);
        skuPerformItemConfigDO2.setBizType(1);
        skuPerformItemConfigDO2.setCycle(cycle);
        skuPerformItemConfigDO2.setPeriodType(PeriodTypeEnum.FIX_DAY.toInt());
        skuPerformItemConfigDO2.setRightId(32423);
        skuPerformItemConfigDO2.setPeriodCount(31);
        skuPerformItemConfigDO2.setRightType(2);
        skuPerformItemConfigDO2.setProviderId("1");
        rightViewInfo = new RightViewInfo();
        rightViewInfo.setDisplayName("会员折扣券权益");
        skuPerformItemConfigDO2.setViewInfo(rightViewInfo);

        skuPerformConfigDO.setConfigs(ImmutableList.of(skuPerformItemConfigDO, skuPerformItemConfigDO2));
        skuInfoDO.setPerformConfig(skuPerformConfigDO);
        return skuInfoDO;
    }
}