/**
 * @(#)IdGenerator.java, 十二月 19, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.id;

import com.memberclub.common.extension.BaseExtension;

/**
 * @author yuhaiqiang
 */
public interface IdGenerator extends BaseExtension {

    public String generateId();
}