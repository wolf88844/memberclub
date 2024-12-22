/**
 * @(#)AftersalePreviewService.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.aftersale;

import com.memberclub.common.extension.BizSceneBuildExtension;
import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.log.LogDomainEnum;
import com.memberclub.common.log.UserLog;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.dataobject.aftersale.AftersaleUnableCode;
import com.memberclub.domain.dataobject.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.dataobject.aftersale.preview.AfterSalePreviewRespose;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.exception.AfterSaleUnableException;
import com.memberclub.infrastructure.mybatis.mappers.MemberOrderDao;
import com.memberclub.sdk.extension.aftersale.AftersaleCollectDataExtension;
import com.memberclub.sdk.extension.aftersale.AftersalePreviewExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersalePreviewService {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private MemberOrderDao memberOrderDao;

    @UserLog(domain = LogDomainEnum.AFTER_SALE)
    public AfterSalePreviewRespose preview(AfterSalePreviewCmd cmd) {
        AftersaleCollectDataExtension aftersaleCollectDataExtension
                = extensionManager.getExtension(BizScene.of(cmd.getBizType().toBizType()), AftersaleCollectDataExtension.class);
        AftersalePreviewContext context = aftersaleCollectDataExtension.collect(cmd);
        context.setCmd(cmd);

        BizSceneBuildExtension bizSceneBuildExtension =
                extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()));
        String previewExtensionScene = bizSceneBuildExtension.buildAftersalePreviewScene(context);

        AftersalePreviewExtension previewExtension = extensionManager.getExtension(
                BizScene.of(cmd.getBizType().toBizType(), previewExtensionScene),
                AftersalePreviewExtension.class);

        AfterSalePreviewRespose respose = new AfterSalePreviewRespose();
        try {
            previewExtension.preview(context);
            respose.setSuccess(true);
            respose.setRecommendRefundPriceFen(context.getRecommendRefundPrice());
            respose.setRefundType(context.getRefundType());
            respose.setRefundWay(context.getRefundWay());
        } catch (Exception e) {
            if (e.getCause() instanceof AfterSaleUnableException) {
                CommonLog.warn("售后预览流程返回不可退", e);
                respose.setUnableCode(((AfterSaleUnableException) e.getCause()).getUnableCode());
                respose.setSuccess(false);
                respose.setUnableTip(e.getMessage());
            } else {
                CommonLog.error("售后预览流程异常", e);
                respose.setSuccess(false);
                respose.setUnableCode(AftersaleUnableCode.INTERNAL_ERROR.toInt());
                respose.setUnableTip(AftersaleUnableCode.INTERNAL_ERROR.toString());
            }
        }
        return respose;
    }

}