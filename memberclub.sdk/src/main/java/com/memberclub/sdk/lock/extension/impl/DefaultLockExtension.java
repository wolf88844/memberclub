/**
 * @(#)DefaultLockExtension.java, 一月 23, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.lock.extension.impl;

import com.memberclub.common.annotation.Route;
import com.memberclub.common.extension.ExtensionProvider;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.sdk.lock.extension.LockExtension;

/**
 * author: 掘金五阳
 */
@ExtensionProvider(desc = "默认锁扩展点", bizScenes = {@Route(bizType = BizTypeEnum.DEFAULT)})
public class DefaultLockExtension implements LockExtension {
}