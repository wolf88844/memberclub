/**
 * @(#)PreFinanceDataObjectFactory.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.service.domain;

import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.oncetask.common.OnceTaskStatusEnum;
import com.memberclub.domain.context.oncetask.common.TaskTypeEnum;
import com.memberclub.domain.context.prefinance.PreFinanceContext;
import com.memberclub.domain.context.prefinance.common.PreFinanceRecordStatusEnum;
import com.memberclub.domain.context.prefinance.task.FinanceTaskContent;
import com.memberclub.domain.dataobject.perform.MemberPerformItemDO;
import com.memberclub.domain.dataobject.prefinance.PreFinanceRecordDO;
import com.memberclub.domain.dataobject.task.OnceTaskDO;
import com.memberclub.domain.entity.trade.PreFinanceRecord;
import com.memberclub.infrastructure.mapstruct.PreFinanceConvertor;
import org.springframework.stereotype.Service;

/**
 * author: 掘金五阳
 */
@Service
public class PreFinanceDataObjectFactory {

    public PreFinanceRecord buildRecord(PreFinanceRecordDO record) {
        PreFinanceRecord preFinanceRecord = PreFinanceConvertor.INSTANCE.toPreFinanceRecord(record);
        preFinanceRecord.setBizType(record.getBizType().getCode());
        preFinanceRecord.setStatus(record.getStatus().getCode());
        return preFinanceRecord;
    }

    public PreFinanceRecordDO buildRecord(PreFinanceRecord record) {
        PreFinanceRecordDO preFinanceRecord = PreFinanceConvertor.INSTANCE.toPreFinanceRecordDO(record);
        preFinanceRecord.setBizType(BizTypeEnum.findByCode(record.getBizType()));
        preFinanceRecord.setStatus(PreFinanceRecordStatusEnum.findByCode(record.getStatus()));
        return preFinanceRecord;
    }


    public OnceTaskDO buildFinanceExpireTask(PreFinanceContext context, MemberPerformItemDO performItem) {
        OnceTaskDO task = new OnceTaskDO();
        task.setBizType(context.getBizType());
        task.setCtime(TimeUtil.now());
        task.setUtime(TimeUtil.now());
        task.setEtime(performItem.getEtime());
        task.setStime(performItem.getStime());
        task.setStatus(OnceTaskStatusEnum.INIT);
        task.setTaskContentClassName(FinanceTaskContent.class.getName());
        task.setTaskGroupId(String.valueOf(context.getSubTradeId()));
        task.setTaskToken(performItem.getItemToken());
        task.setTaskType(TaskTypeEnum.FINANCE_EXPIRE);
        task.setTradeId(context.getTradeId());
        task.setUserId(context.getUserId());

        FinanceTaskContent content = new FinanceTaskContent();
        content.setEtime(performItem.getEtime());
        content.setStime(performItem.getStime());
        content.setItemToken(performItem.getItemToken());
        content.setPhase(performItem.getPhase());
        content.setSkuId(performItem.getSkuId());
        content.setSubTradeId(context.getSubTradeId());
        content.setTradeId(context.getTradeId());
        task.setContent(content);
        return task;
    }
}