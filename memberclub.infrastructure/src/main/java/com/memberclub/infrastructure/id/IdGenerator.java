/**
 * @(#)IdGenerator.java, 十二月 19, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.id;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;

/**
 * @author yuhaiqiang
 */
@ExtensionConfig(desc = "分布式 ID 生成扩展点", type = ExtensionType.COMMON)
public interface IdGenerator extends BaseExtension {

    public Long generateId(IdTypeEnum idType);
}