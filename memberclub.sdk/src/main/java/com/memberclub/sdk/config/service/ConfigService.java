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

    /**
     * 根据业务场景查找配置表
     * 本方法通过扩展管理器获取与特定业务场景关联的配置表对象
     * 主要用于解耦业务逻辑和配置管理，通过场景参数动态确定配置来源
     *
     * @param scene 业务场景，用于确定使用哪一种配置
     * @return BizConfigTable 配置表对象，包含业务配置信息
     */
    public BizConfigTable findConfigTable(BizScene scene) {
        return extensionManager.getExtension(scene, BizConfigTable.class);
    }
}