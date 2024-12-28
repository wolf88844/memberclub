/**
 * @(#)SingleMemberPerformHisFlow.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.flow.perform.execute;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.flow.FlowNode;
import com.memberclub.common.log.CommonLog;
import com.memberclub.domain.common.MemberPerformHisStatusEnum;
import com.memberclub.domain.context.perform.PerformContext;
import com.memberclub.domain.context.perform.SkuPerformContext;
import com.memberclub.domain.entity.MemberPerformHis;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.mybatis.mappers.MemberPerformHisDao;
import com.memberclub.sdk.extension.perform.execute.MemberPerformHisExtension;
import com.memberclub.sdk.service.domain.PerformDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class SingleMemberPerformHisFlow extends FlowNode<PerformContext> {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private MemberPerformHisDao memberPerformHisDao;

    @Autowired
    private PerformDomainService performDomainService;

    @Override
    public void process(PerformContext context) {
        SkuPerformContext skuPerformContext = context.getSkuPerformContexts().get(0);
        context.setCurrentSkuPerformContext(skuPerformContext);

        MemberPerformHisExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberPerformHisExtension.class);
        MemberPerformHis memberPerformHis = extension.toMemberPerformHis(context, skuPerformContext);

        int cnt = performDomainService.insertMemberPerformHis(memberPerformHis);
        if (cnt > 0) {
            CommonLog.warn("写入 member_perform_his 成功: {}", memberPerformHis);
            return;
        }
        MemberPerformHis hisFromDb = memberPerformHisDao.selectBySkuId(context.getUserId(),
                context.getTradeId(), skuPerformContext.getHis().getSkuId());
        if (hisFromDb == null) {
            CommonLog.error("写入 member_perform_his失败", memberPerformHis);
            ResultCode.INTERNAL_ERROR.throwException();
        }

        memberPerformHis.setPerformHisToken(hisFromDb.getPerformHisToken());
        if (MemberPerformHisStatusEnum.hasPerformed(hisFromDb.getStatus())) {

            CommonLog.error(" member_perform_his已履约完成,无需再次履约:{}", memberPerformHis);
            // TODO: 2024/12/15 如何处理返回值
            return;
        }
        CommonLog.error(" member_perform_his已存在,但未履约成功,继续履约:{}", memberPerformHis);
    }


    @Override
    public void success(PerformContext context) {
        SkuPerformContext skuPerformContext = context.getSkuPerformContexts().get(0);
        MemberPerformHisExtension extension = extensionManager.getExtension(context.toDefaultScene(), MemberPerformHisExtension.class);
        MemberPerformHis memberPerformHis = extension.toMemberPerformHisWhenPerformSuccess(context, skuPerformContext);

        performDomainService.performSuccess(memberPerformHis);
    }
}