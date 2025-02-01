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
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.context.purchase.PurchaseSubmitResponse;
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelCmd;
import com.memberclub.domain.context.purchase.cancel.PurchaseCancelContext;
import com.memberclub.domain.context.purchase.common.MemberOrderStatusEnum;
import com.memberclub.domain.exception.MemberException;
import com.memberclub.domain.exception.ResultCode;
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

    public void cancel(PurchaseCancelCmd cmd) {
        PurchaseCancelContext context = new PurchaseCancelContext();
        context.setCmd(cmd);
        try {
            extensionManager.getExtension(BizScene.of(context.getCmd().getBizType()),
                    PurchaseExtension.class).cancel(context);
        } catch (MemberException e) {
            throw e;
        } catch (Exception e) {
            MemberException me = ResultCode.PURCHASE_CANCEL_ERROR.newException("开通取消流程异常", e);
            throw me;
        }
    }

    public void reverse(AfterSaleApplyContext context) {
        try {
            extensionManager.getExtension(
                    BizScene.of(context.getCmd().getBizType()), PurchaseExtension.class).reverse(context);
        } catch (MemberException e) {
            throw e;
        } catch (Exception e) {
            MemberException me = ResultCode.PURCHASE_REVERSE_ERROR.newException("开通逆向流程异常", e);
            throw me;
        }
    }


    @UserLog(domain = LogDomainEnum.PURCHASE)
    public PurchaseSubmitResponse submit(PurchaseSubmitCmd cmd) {
        cmd.isValid();

        PurchaseSubmitContext context = new PurchaseSubmitContext(cmd);
        PurchaseSubmitResponse response = new PurchaseSubmitResponse();
        try {
            extensionManager.getExtension(
                    context.toDefaultBizScene(), PurchaseExtension.class).submit(context);
            context.monitor();

            if (context.getMemberOrder().getStatus() == MemberOrderStatusEnum.SUBMITED) {
                response.setSuccess(true);
                response.setMemberOrderDO(context.getMemberOrder());
                response.setLockValue(context.getLockValue());
                return response;
            }
            // TODO: 2025/1/4 补充错误信息
            response.setSuccess(false);
            return response;
        } catch (MemberException e) {
            context.monitorException(e);
            throw e;
        } catch (Exception e) {
            MemberException me = ResultCode.COMMON_ORDER_SUBMIT_ERROR.newException("提单流程异常", e);
            context.monitorException(me);
            throw me;
        }
        // TODO: 2025/1/4 补充返回值
    }


}