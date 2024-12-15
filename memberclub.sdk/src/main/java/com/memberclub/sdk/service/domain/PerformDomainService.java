/**
 * @(#)PerformDomainService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.domain;

import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: 掘金五阳
 */
@Service
public class PerformDomainService {

    @Autowired
    private MemberPerformHisDao memberPerformHisDao;

    @Transactional
    public void performSuccess(MemberPerformHis his) {
        // TODO: 2024/12/15

        memberPerformHisDao.updateStatus(his.getUserId(),
                his.getTradeId(),
                his.getSkuId(),
                his.getStatus(),
                his.getUtime());
    }
}