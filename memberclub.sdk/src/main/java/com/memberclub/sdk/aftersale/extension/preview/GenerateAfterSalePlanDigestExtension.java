/**
 * @(#)GenerateAfterSalePlanDigestExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.aftersale.extension.preview;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.context.aftersale.preview.AftersalePreviewContext;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "构建售后计算摘要扩展点", type = ExtensionType.AFTERSALE)
public interface GenerateAfterSalePlanDigestExtension extends BaseExtension {

    public void generateDigest(AftersalePreviewContext context);

}