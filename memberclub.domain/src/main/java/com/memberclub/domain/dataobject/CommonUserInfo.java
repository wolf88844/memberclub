/**
 * @(#)PerformHisUserInfo.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class CommonUserInfo {

    private String uuid;

    private String phone;

    private String maskedPhone;

    private String encryptedPhone;

    private String wxOpenId;

    private String key;
}