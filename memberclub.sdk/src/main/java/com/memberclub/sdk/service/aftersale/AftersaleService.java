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
import com.memberclub.domain.context.aftersale.contant.AftersaleUnableCode;
import com.memberclub.domain.context.aftersale.apply.AfterSaleApplyContext;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyCmd;
import com.memberclub.domain.context.aftersale.apply.AftersaleApplyResponse;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewCmd;
import com.memberclub.domain.context.aftersale.preview.AfterSalePreviewResponse;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;
import com.memberclub.domain.exception.AfterSaleUnableException;
import com.memberclub.infrastructure.mapstruct.PerformConvertor;
import com.memberclub.sdk.extension.aftersale.apply.AfterSaleApplyExtension;
import com.memberclub.sdk.extension.aftersale.preview.AftersaleCollectDataExtension;
import com.memberclub.sdk.extension.aftersale.preview.AftersalePreviewExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class AftersaleService {

    @Autowired
    private ExtensionManager extensionManager;

    @UserLog(domain = LogDomainEnum.AFTER_SALE)
    public AfterSalePreviewResponse preview(AfterSalePreviewCmd cmd) {
        AfterSalePreviewResponse respose = new AfterSalePreviewResponse();
        try {
            AftersalePreviewContext context = doPreview(cmd);
            respose.setSuccess(true);
            respose.setRecommendRefundPriceFen(context.getRecommendRefundPrice());
            respose.setRefundType(context.getRefundType());
            respose.setRefundWay(context.getRefundWay());
            respose.setDigests(context.getDigests());
            respose.setDigestVersion(context.getDigestVersion());
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

    public AftersalePreviewContext doPreview(AfterSalePreviewCmd cmd) {
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

        previewExtension.preview(context);
        return context;
    }

    @UserLog(domain = LogDomainEnum.AFTER_SALE)
    public AftersaleApplyResponse apply(AftersaleApplyCmd cmd) {
        AfterSalePreviewCmd previewCmd = PerformConvertor.INSTANCE.toPreviewCmd(cmd);
        previewCmd.setDigestVersion(cmd.getDigestVersion());

        AftersaleApplyResponse response = new AftersaleApplyResponse();

        AftersalePreviewContext previewContext;
        try {
            previewContext = doPreview(previewCmd);
        } catch (Exception e) {
            if (e.getCause() instanceof AfterSaleUnableException) {
                CommonLog.warn("售后预览流程返回不可退", e);
                response.setUnableCode(((AfterSaleUnableException) e.getCause()).getUnableCode());
                response.setSuccess(false);
                response.setUnableTip(e.getMessage());
            } else {
                CommonLog.error("售后预览流程异常", e);
                response.setSuccess(false);
                response.setUnableCode(AftersaleUnableCode.INTERNAL_ERROR.toInt());
                response.setUnableTip(AftersaleUnableCode.INTERNAL_ERROR.toString());
            }
            return response;
        }
        AfterSaleApplyContext context = new AfterSaleApplyContext();
        context.setPreviewContext(previewContext);


        try {
            BizSceneBuildExtension bizSceneBuildExtension =
                    extensionManager.getSceneExtension(BizScene.of(cmd.getBizType().toBizType()));
            String applyExtensionScene = bizSceneBuildExtension.buildAftersaleApplyScene(context);

            AfterSaleApplyExtension applyExtension = extensionManager.getExtension(
                    BizScene.of(cmd.getBizType().toBizType(), applyExtensionScene),
                    AfterSaleApplyExtension.class);
            context.setCmd(cmd);

            applyExtension.apply(context);
            response.setSuccess(true);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return response;
    }

}