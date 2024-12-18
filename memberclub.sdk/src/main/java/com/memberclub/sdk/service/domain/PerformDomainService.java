/**
 * @(#)PerformDomainService.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.domain;

import com.memberclub.domain.common.PerformItemStatusEnum;
import com.memberclub.domain.dataobject.perform.PerformItemContext;
import com.memberclub.domain.dataobject.perform.PerformItemDO;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformItemDao;
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

    @Autowired
    private MemberPerformItemDao memberPerformItemDao;


    @Transactional
    public void performSuccess(MemberPerformHis his) {
        // TODO: 2024/12/15

        memberPerformHisDao.updateStatus(his.getUserId(),
                his.getTradeId(),
                his.getSkuId(),
                his.getStatus(),
                his.getUtime());
    }

    public void itemPerformSuccess(PerformItemContext context) {
        for (PerformItemDO item : context.getItems()) {
            memberPerformItemDao.update2Status(context.getPerformContext().getUserId(),
                    item.getItemToken(),
                    item.getBatchCode(),
                    PerformItemStatusEnum.PERFORM_SUCC.toInt());
        }
    }

}