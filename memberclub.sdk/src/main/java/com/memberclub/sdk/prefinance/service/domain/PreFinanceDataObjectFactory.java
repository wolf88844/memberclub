/**
 * @(#)PreFinanceDataObjectFactory.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.prefinance.service.domain;

import com.memberclub.domain.common.BizTypeEnum;
import com.memberclub.domain.context.prefinance.common.PreFinanceRecordStatusEnum;
import com.memberclub.domain.dataobject.prefinance.PreFinanceRecordDO;
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

}