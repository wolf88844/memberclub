/**
 * @(#)MessageQueueConsumerFacade.java, 一月 12, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mq;

/**
 * @author yuhaiqiang
 * <p>
 * MQ 消费防腐层, 提供了默认的实现类,一般情况下不需要额外扩展.需要注意的是
 * 在ut模式下使用 Local 模式实现 MQ, 在 test,online等正式环境,你应该依托于MQ 中间件
 * 实现消费订阅, 你应该在 MQ 消费逻辑中,传入 MQEventEnum 和消息体 调用MessageQueueConsumerFacade
 * 实现订阅逻辑
 */
public interface MessageQueueConsumerFacade {

    public MQQueueEnum register();

    public void consume(String message);
}