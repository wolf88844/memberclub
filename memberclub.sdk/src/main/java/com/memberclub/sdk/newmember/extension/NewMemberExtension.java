/**
 * @(#)NewMemberExtension.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.newmember.extension;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.common.extension.ExtensionConfig;
import com.memberclub.common.extension.ExtensionType;
import com.memberclub.domain.dataobject.newmember.NewMemberMarkContext;

/**
 * author: 掘金五阳
 */
@ExtensionConfig(desc = "新会员标记扩展点", must = false, type = ExtensionType.PURCHASE)
public interface NewMemberExtension extends BaseExtension {

    public void buildUserTagOpList(NewMemberMarkContext context);
}