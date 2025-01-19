/**
 * @(#)SkuTest.java, 一月 19, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.google.common.collect.ImmutableList;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.ApplicationContextUtils;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.perform.common.PeriodTypeEnum;
import com.memberclub.domain.dataobject.sku.SkuFinanceInfo;
import com.memberclub.domain.dataobject.sku.SkuInfoDO;
import com.memberclub.domain.dataobject.sku.SkuPerformItemConfigDO;
import com.memberclub.domain.dataobject.sku.SkuSaleInfo;
import com.memberclub.domain.dataobject.sku.SkuViewInfo;
import com.memberclub.domain.dataobject.sku.SkuPerformConfigDO;
import com.memberclub.domain.dataobject.sku.rights.RightViewInfo;
import com.memberclub.domain.entity.sku.MemberSku;
import com.memberclub.infrastructure.mybatis.mappers.sku.MemberSkuDao;
import com.memberclub.sdk.sku.service.SkuDomainService;
import com.memberclub.starter.mock.MockBaseTest;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * author: 掘金五阳
 */
public class SkuTest extends MockBaseTest {

    @Autowired
    MemberSkuDao memberSkuDao;

    public SkuInfoDO buildDoubleRightsSku(int cycle) {
        SkuInfoDO skuInfoDO = new SkuInfoDO();

        skuInfoDO.setSkuId(RandomUtils.nextInt());
        skuInfoDO.setBizType(BizTypeEnum.DEMO_MEMBER.getCode());

        SkuSaleInfo skuSaleInfo = new SkuSaleInfo();
        skuSaleInfo.setOriginPriceFen(3000);
        skuSaleInfo.setSalePriceFen(699);

        skuInfoDO.setSaleInfo(skuSaleInfo);

        SkuFinanceInfo settleInfo = new SkuFinanceInfo();
        settleInfo.setContractorId("438098434");
        settleInfo.setSettlePriceFen(300);

        skuInfoDO.setFinanceInfo(settleInfo);

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
        skuPerformItemConfigDO.setPeriodType(PeriodTypeEnum.FIX_DAY.getCode());
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
        skuPerformItemConfigDO2.setPeriodType(PeriodTypeEnum.FIX_DAY.getCode());
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


    @Autowired
    private SkuDomainService skuDomainService;

    @Test
    public void testAddSku() {
        if (!ApplicationContextUtils.isUnitTest()) {
            CommonLog.info("当前 Profile:{} 不执行本单测",
                    JsonUtils.toJson(ApplicationContextUtils.getContext().getEnvironment().getActiveProfiles()));
            return;
        }

        SkuInfoDO skuInfoDO = buildDoubleRightsSku(1);

        MemberSku sku = new MemberSku();
        sku.setBizType(skuInfoDO.getBizType());
        sku.setExtra("{}");
        sku.setFinanceInfo(JsonUtils.toJson(skuInfoDO.getFinanceInfo()));
        sku.setId(RandomUtils.nextLong());
        sku.setInventoryInfo("{}");
        sku.setPerformanceInfo("{}");
        sku.setRestrictInfo("{}");
        sku.setSaleInfo("{}");
        sku.setViewInfo("{}");
        sku.setStatus(0);

        sku.setUtime(TimeUtil.now());
        sku.setCtime(TimeUtil.now());

        skuDomainService.createMemberSku(sku);
    }
}