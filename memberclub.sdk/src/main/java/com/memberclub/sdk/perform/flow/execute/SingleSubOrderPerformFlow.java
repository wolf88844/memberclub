/**
 * @(#)SingleMemberPerformHisFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.perform.flow.execute;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.entity.MemberSubOrder;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberSubOrderDao;
import com.memberclub.sdk.perform.extension.execute.MemberSubOrderPerformExtension;
import com.memberclub.sdk.perform.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class SingleSubOrderPerformFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private MemberSubOrderDao memberSubOrderDao;

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(PerformContext context) {
        SkuPerformContext skuPerformContext = context.getSkuPerformContexts().get(0);
        context.setCurrentSkuPerformContext(skuPerformContext);

        MemberSubOrderPerformExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberSubOrderPerformExtension.class);
        MemberSubOrder memberSubOrder = extension.toMemberSubOrder(context, skuPerformContext);

        int cnt = performDomainService.insertMemberSubOrder(memberSubOrder);
        if (cnt > 0) {
            CommonLog.warn("写入 member_perform_his 成功: {}", memberSubOrder);
            return;
        }
        MemberSubOrder hisFromDb = memberSubOrderDao.selectBySkuId(context.getUserId(),
                context.getTradeId(), skuPerformContext.getHis().getSkuId());
        if (hisFromDb == null) {
            CommonLog.error("写入 member_perform_his失败", memberSubOrder);
            ResultCode.INTERNAL_ERROR.throwException();
        }

        memberSubOrder.setSubOrderToken(hisFromDb.getSubOrderToken());
        if (SubOrderPerformStatusEnum.hasPerformed(hisFromDb.getStatus())) {

            CommonLog.error(" member_perform_his已履约完成,无需再次履约:{}", memberSubOrder);
            // TODO: 2024/12/15 如何处理返回值
            return;
        }
        CommonLog.error(" member_perform_his已存在,但未履约成功,继续履约:{}", memberSubOrder);
    }


    @Override
    public void success(PerformContext context) {
        SkuPerformContext skuPerformContext = context.getSkuPerformContexts().get(0);
        MemberSubOrderPerformExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberSubOrderPerformExtension.class);
        MemberSubOrder memberSubOrder = extension.toMemberSubOrderWhenPerformSuccess(context, skuPerformContext);

        performDomainService.performSuccess(memberSubOrder);
    }
}