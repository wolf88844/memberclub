/**
 * @(#)LockService.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.service.perform;

import com.memberclub.common.extension.ExtensionManager;
import com.memberclub.common.log.CommonLog;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizScene;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.exception.ResultCode;
import com.memberclub.infrastructure.lock.DistributeLock;
import com.memberclub.sdk.common.LockMode;
import com.memberclub.sdk.common.Monitor;
import com.memberclub.sdk.common.SwitchEnum;
import com.memberclub.sdk.extension.config.BizConfigTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class LockService {

    @Autowired
    private ExtensionManager extensionManager;

    @Autowired
    private DistributeLock distributeLock;

    public String lock(BizTypeEnum biztype, String lockValue, long userId, String tradeId) {
        BizConfigTable table = extensionManager.getExtension(BizScene.of(biztype), BizConfigTable.class);

        String key = buildKey(biztype, userId, tradeId, table);
        if (lockValue == null) {
            lockValue = String.valueOf(TimeUtil.now());
        }

        boolean locked = distributeLock.lock(key, lockValue,
                SwitchEnum.LOCK_TIMEOUT_SECONDS.getInt(biztype.toBizType()));
        if (!locked) {
            CommonLog.error("加锁失败,需要再次重试 key:{}, value:{}", key, lockValue);
            Monitor.PERFORM_EXECUTE.counter(biztype, "lock", false);
            ResultCode.LOCK_ERROR.throwException();
        }

        CommonLog.info("加锁成功 key:{}, value:{}", key, lockValue);
        Monitor.PERFORM_EXECUTE.counter(biztype, "lock", true);
        return lockValue;
    }

    private String buildKey(BizTypeEnum bizType, long userId, String tradeId, BizConfigTable table) {
        String format = "%s_%s_lock";
        String key = null;
        if (table.getLockMode() == LockMode.LOCK_ORDER) {
            key = String.format(format, bizType.toBizType(), tradeId);
        } else if (table.getLockMode() == LockMode.LOCK_USER) {
            key = String.format(format, bizType.toBizType(), userId);
        }
        return key;
    }

    public void unlock(BizTypeEnum bizType, long userId, String tradeId, String lockValue) {
        BizConfigTable table = extensionManager.getExtension(
                BizScene.of(bizType), BizConfigTable.class);
        String key = buildKey(bizType, userId, tradeId, table);

        //内部重试解锁,务必确保解锁成功
        boolean succ = distributeLock.unlock(key, lockValue);
        CommonLog.info("尝试解锁完成 key:{}, value:{} ,succ:{}", key, lockValue, succ);

        Monitor.PERFORM_EXECUTE.counter(bizType, "unlock", "true");
    }

    public void rollbackLock(BizTypeEnum bizType, long userId, String tradeId, String lockValue, int retryTimes) {
        BizConfigTable table = extensionManager.getExtension(BizScene.of(bizType), BizConfigTable.class);
        String key = buildKey(bizType, userId, tradeId, table);
        CommonLog.error("回滚阶段尝试解锁 key:{}, value:{}", key, lockValue);
        Monitor.PERFORM_EXECUTE.counter(bizType, "unlock", "rollback");
        if (table.getLockMode() == LockMode.LOCK_ORDER) {
            distributeLock.unlock(key, lockValue);
        } else if (table.getLockMode() == LockMode.LOCK_USER) {
            if (retryTimes >= SwitchEnum.PERFORM_RETRY_MAX_TIME.getInt(bizType.toBizType())) {
                distributeLock.unlock(key, lockValue);
            } else {
                CommonLog.warn("用户维度锁,在履约失败时,暂时不释放");
            }
        }
    }
}