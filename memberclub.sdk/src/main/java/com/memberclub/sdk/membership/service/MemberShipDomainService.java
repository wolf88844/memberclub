/**
 * @(#)MemberShipDomainService.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.membership.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.membership.MemberShipDO;
import com.memberclub.domain.dataobject.membership.MemberShipStatusEnum;
import com.memberclub.domain.entity.trade.MemberShip;
import com.memberclub.infrastructure.mybatis.mappers.trade.MemberShipDao;
import com.memberclub.sdk.membership.extension.MemberShipDomainExtension;
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
    private ExtensionManager extensionManager;

    @Autowired
    private MemberShipDataObjectFactory memberShipDataObjectFactory;


    public MemberShipDO getMemberShipDO(long userId, String grantCode) {
        LambdaQueryWrapper<MemberShip> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(MemberShip::getGrantCode, grantCode);
        wrapper.eq(MemberShip::getUserId, userId);
        MemberShip memberShip = memberShipDao.selectOne(wrapper);
        return memberShipDataObjectFactory.buildMemberShipDO(memberShip);
    }


    @Transactional(rollbackFor = Exception.class)
    public void grant(MemberShipDO memberShipDO) {
        memberShipDO.onFinish();
        MemberShip memberShip = memberShipDataObjectFactory.buildMemberShip(memberShipDO);
        extensionManager.getExtension(BizScene.of(memberShipDO.getBizType()),
                MemberShipDomainExtension.class).onGrant(memberShipDO, memberShip);
    }

    @Transactional(rollbackFor = Exception.class)
    public void cancel(MemberShipDO memberShipDO) {
        if (memberShipDO.getStatus() == MemberShipStatusEnum.CANCEL) {
            CommonLog.warn("会员身份发放记录已经取消, 无需再次取消 memberShipDO:{}", memberShipDO);
            return;
        }
        memberShipDO.onCancel();

        LambdaUpdateWrapper<MemberShip> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(MemberShip::getUserId, memberShipDO.getUserId());
        wrapper.eq(MemberShip::getItemToken, memberShipDO.getItemToken());
        wrapper.set(MemberShip::getStatus, memberShipDO.getStatus().getCode());
        wrapper.set(MemberShip::getUtime, memberShipDO.getUtime());

        extensionManager.getExtension(BizScene.of(memberShipDO.getBizType()),
                MemberShipDomainExtension.class).onCancel(memberShipDO, wrapper);
    }
}