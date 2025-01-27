/**
 * @(#)MessageQueueDebugFacade.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

import java.util.List;

/**
 * author: 掘金五阳
 */
public interface MessageQueueDebugFacade extends MessageQuenePublishFacade {

    public List<String> getMessage(String topic);

    public void resetMsgs(String topic);
}