/**
 * @(#)InventoryRecord.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers.sku;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.inventory.InventoryRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: 掘金五阳
 */
@DS("skuDataSource")
@Mapper
public interface InventoryRecordDao extends BaseMapper<InventoryRecord> {

    public static final String TABLE_NAME = "inventory_record";

    Integer insertIgnoreBatch(List<InventoryRecord> records);

    @QueryMaster
    @Select({
            "SELECT * FROM ", TABLE_NAME,
            " WHERE user_id=#{userId} AND inventory_key=#{inventoryKey} ",
            " AND op_type=#{opType} AND operate_key=#{operateKey}"
    })
    public InventoryRecord queryRecord(@Param("userId") long userId,
                                       @Param("operateKey") String operateKey,
                                       @Param("inventoryKey") String inventoryKey,
                                       @Param("opType") int opType);
}