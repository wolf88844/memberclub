/**
 * @(#)MemberShipDomainService.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.membership.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.google.common.collect.Lists;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.dataobject.membership.MemberShipDO;
import com.memberclub.domain.entity.trade.MemberShip;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberShipDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: 掘金五阳
 */
@DS("tradeDataSource")
@Service
public class MemberShipDomainService {

    @Autowired
    private MemberShipDao memberShipDao;

    @Autowired
    private MemberShipDataObjectFactory memberShipDataObjectFactory;

    @Transactional(rollbackFor = Exception.class)
    public void grant(MemberShipDO memberShipDO) {
        memberShipDO.onFinish();
        MemberShip memberShip = memberShipDataObjectFactory.buildMemberShip(memberShipDO);

        int cnt = memberShipDao.insertIgnoreBatch(Lists.newArrayList(memberShip));
        if (cnt > 0) {
            CommonLog.info("成功写入会员身份:{}", memberShipDO);
            return;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(MemberShipDO memberShipDO) {
        LambdaUpdateWrapper<MemberShip> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberShip::getUserId, memberShipDO.getUserId());
        wrapper.eq(MemberShip::getItemToken, memberShipDO.getItemToken());
        wrapper.set(MemberShip::getStatus, memberShipDO.getStatus().getCode());
        wrapper.set(MemberShip::getUtime, memberShipDO.getUtime());

        int cnt = memberShipDao.update(null, wrapper);
        if (cnt <= 0) {
            CommonLog.error("取消会员身份失败:{}", memberShipDO);
            return;
        }
        CommonLog.error("取消会员身份成功:{}", memberShipDO);
    }
}