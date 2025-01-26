/**
 * @(#)PreFinanceQueryAssetsFlow.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.flow;

import com.memberclub.common.flow.FlowNode;
import com.memberclub.domain.context.prefinance.PreFinanceContext;
import com.memberclub.sdk.prefinance.service.domain.PreFinanceDomainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PreFinanceQueryAssetsFlow extends FlowNode<PreFinanceContext> {

    @Autowired
    PreFinanceDomainService preFinanceAssetsDomainService;

    @Override
    public void process(PreFinanceContext preFinanceContext) {
        preFinanceAssetsDomainService.buildAssets(preFinanceContext);
    }
}