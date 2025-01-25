/**
 * @(#)PurchaseBizService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.purchase.service.biz;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitResponse;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.exception.MemberException;
import com.memberclub.sdk.purchase.extension.PurchaseExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PurchaseBizService {

    @Autowired
    private ExtensionManager extensionManager;


    public void reverse(AfterSaleApplyContext context) {
        // TODO: 2025/1/5
    }


    @UserLog(domain = LogDomainEnum.PURCHASE)
    public PurchaseSubmitResponse submit(PurchaseSubmitCmd cmd) {
        cmd.isValid();

        PurchaseSubmitContext context = new PurchaseSubmitContext(cmd);
        PurchaseSubmitResponse response = new PurchaseSubmitResponse();
        try {
            extensionManager.getExtension(
                    context.toDefaultBizScene(), PurchaseExtension.class)
                    .submit(context);
            context.monitor();

            if (context.getMemberOrder().getStatus() == MemberOrderStatusEnum.SUBMITED) {
                response.setSuccess(true);
                response.setMemberOrderDO(context.getMemberOrder());
                return response;
            }
            // TODO: 2025/1/4 补充错误信息
            response.setSuccess(false);
            return response;
        } catch (MemberException e) {
            context.monitorException(e);
            throw e;
        }
        // TODO: 2025/1/4 补充返回值
    }




}