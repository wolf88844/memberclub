/**
 * @(#)RetryService.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.retry;

/**
 * author: 掘金五阳
 */
public interface RetryService {

    public void addRetryMessage(RetryMessage message);

    public void consumeMessage(RetryMessage message);

    
}