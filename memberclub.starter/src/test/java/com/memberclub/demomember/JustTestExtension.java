/**
 * @(#)PerformExtension.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.demomember;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.context.perform.PerformCmd;

/**
 * @author 掘金五阳
 */
public interface JustTestExtension extends BaseExtension {

    public void execute(PerformCmd cmd) throws Exception;
}