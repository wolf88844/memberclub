/**
 * @(#)AfterSaleApplyContext.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.aftersale.apply;

import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class AfterSaleApplyContext {

    private AftersaleApplyCmd cmd;

    private AftersalePreviewContext previewContext;


}