/**
 * @(#)PerformContextBuildExtension.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.perform;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.perform.PerformCmd;
import com.memberclub.domain.dataobject.perform.PerformContext;

/**
 * author: 掘金五阳
 */
public interface PreBuildPerformContextExtension extends BaseExtension {

    public PerformContext preBuild(PerformCmd cmd);

}