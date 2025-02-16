/**
 * @(#)ConfigService.java, 十二月 31, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.config.service;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.domain.common.BizScene;
import com.memberclub.sdk.config.extension.BizConfigTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class ConfigService {

    @Autowired
    private ExtensionManager extensionManager;

    public BizConfigTable findConfigTable(BizScene scene) {
        return extensionManager.getExtension(scene, BizConfigTable.class);
    }
}