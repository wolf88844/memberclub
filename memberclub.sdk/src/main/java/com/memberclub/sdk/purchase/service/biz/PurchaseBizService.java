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
import com.memberclub.domain.context.purchase.PurchaseSubmitCmd;
import com.memberclub.domain.context.purchase.PurchaseSubmitContext;
import com.memberclub.domain.exception.MemberException;
import com.memberclub.sdk.purchase.extension.PurchaseSubmitExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PurchaseBizService {

    @Autowired
    private ExtensionManager extensionManager;

    @UserLog(domain = LogDomainEnum.PURCHASE)
    public void submit(PurchaseSubmitCmd cmd) {
        cmd.isValid();

        PurchaseSubmitContext context = new PurchaseSubmitContext(cmd);

        try {
            extensionManager.getExtension(context.toDefaultBizScene(),
                    PurchaseSubmitExtension.class).submit(context);

            context.monitor();
        } catch (MemberException e) {
            context.monitorException(e);
            throw e;
        }
        // TODO: 2025/1/4 补充返回值
    }


}