/**
 * @(#)AftersalePreviewExtension.java, 十二月 22, 2024.
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
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "售后预览扩展点", type = ExtensionType.AFTERSALE)
public interface AftersalePreviewExtension extends BaseExtension {

    public void preview(AftersalePreviewContext context);
}