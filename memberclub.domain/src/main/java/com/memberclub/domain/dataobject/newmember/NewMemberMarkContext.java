/**
 * @(#)NewMemberMarkContext.java, 一月 31, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.newmember;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.dataobject.sku.NewMemberInfoDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class NewMemberMarkContext {

    private long userId;

    private String uniqueKey;

    private BizTypeEnum bizType;

    private String phone;

    private long skuId;

    private NewMemberInfoDO newMemberInfo;

    List<UserTagOpDO> userTagOpDOList;

    private boolean newmember;
}