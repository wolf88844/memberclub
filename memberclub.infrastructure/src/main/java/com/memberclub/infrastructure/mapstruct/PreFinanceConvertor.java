/**
 * @(#)PreFinanceConvertor.java, 一月 25, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mapstruct;

import com.memberclub.domain.dataobject.prefinance.PreFinanceRecordDO;
import com.memberclub.domain.entity.trade.PreFinanceRecord;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

/**
 * author: 掘金五阳
 */
@Mapper
public interface PreFinanceConvertor {
    PreFinanceConvertor INSTANCE = Mappers.getMapper(PreFinanceConvertor.class);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bizType", ignore = true)
    public PreFinanceRecord toPreFinanceRecord(PreFinanceRecordDO record);


    @Mapping(target = "status", ignore = true)
    @Mapping(target = "bizType", ignore = true)
    public PreFinanceRecordDO toPreFinanceRecordDO(PreFinanceRecord record);

}