/**
 * @(#)TestStarter.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub;

import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.starter.AppStarter;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.common.util.PeriodUtils;
import com.memberclub.common.util.TimeRange;
import com.memberclub.common.util.TimeUtil;
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

        TimeRange timeRange = PeriodUtils.buildTimeRangeFromNow(2);


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

        TimeRange timeRange = PeriodUtils.buildTimeRangeFromNow(2);


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

}