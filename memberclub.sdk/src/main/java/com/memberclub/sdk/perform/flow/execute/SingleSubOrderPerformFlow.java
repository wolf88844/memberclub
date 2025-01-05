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
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SubOrderPerformContext;
import com.memberclub.domain.context.perform.common.SubOrderPerformStatusEnum;
import com.memberclub.domain.dataobject.perform.MemberSubOrderDO;
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
        SubOrderPerformContext subOrderPerformContext = context.getSubOrderPerformContexts().get(0);
        context.setCurrentSubOrderPerformContext(subOrderPerformContext);

        MemberSubOrderPerformExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberSubOrderPerformExtension.class);
        extension.buildMemberSubOrderOnStartPerform(context, subOrderPerformContext);

        MemberSubOrderDO memberSubOrder = subOrderPerformContext.getSubOrder();
        memberSubOrder.onStartPerform(subOrderPerformContext);

        int cnt = performDomainService.startPerformSubOrder(memberSubOrder);
        if (cnt > 0) {
            CommonLog.warn("写入 member_perform_his 成功: {}", memberSubOrder);
            return;
        }
        MemberSubOrder hisFromDb = memberSubOrderDao.selectBySkuId(context.getUserId(),
                context.getTradeId(), subOrderPerformContext.getSubOrder().getSkuId());
        if (hisFromDb == null) {
            CommonLog.error("member_sub_order缺失!", memberSubOrder);
            throw ResultCode.INTERNAL_ERROR.newException();
        }

        memberSubOrder.setSubTradeId(hisFromDb.getSubTradeId());
        if (SubOrderPerformStatusEnum.hasPerformed(hisFromDb.getPerformStatus())) {
            CommonLog.error(" member_perform_his已履约完成,无需再次履约:{}", memberSubOrder);
            // TODO: 2024/12/15 如何处理返回值
            return;
        }
        CommonLog.error("重试请求,继续履约子单:{}", memberSubOrder);
    }


    @Override
    public void success(PerformContext context) {
        SubOrderPerformContext subOrderPerformContext = context.getSubOrderPerformContexts().get(0);
        MemberSubOrderPerformExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberSubOrderPerformExtension.class);
        extension.buildMemberSubOrderWhenPerformSuccess(context, subOrderPerformContext);

        performDomainService.finishSubOrderPerformOnSuccess(subOrderPerformContext.getSubOrder());
    }
}