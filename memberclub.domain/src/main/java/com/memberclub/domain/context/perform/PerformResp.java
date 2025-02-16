/**
 * @(#)PerformResp.java, 十二月 21, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class PerformResp {

    public boolean success;

    public boolean needRetry;
}