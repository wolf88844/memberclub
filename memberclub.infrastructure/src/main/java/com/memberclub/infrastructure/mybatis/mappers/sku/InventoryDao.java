/**
 * @(#)InventoryDao.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers.sku;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.inventory.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * author: 掘金五阳
 */
@DS("skuDataSource")
@Mapper
public interface InventoryDao extends BaseMapper<Inventory> {

    public static final String TABLE_NAME = "inventory";

    Integer insertIgnoreBatch(List<Inventory> invs);

    @Select({
            "SELECT count(*) FROM ", TABLE_NAME,
            " WHERE target_id =#{targetId} AND target_type=#{targetType} ",
            " AND sub_key = #{subKey} ",
            " AND total_count >= sale_count + #{opCount} ",
    })
    public int isEnough(@Param("targetType") int targetType,
                        @Param("targetId") Long targetId,
                        @Param("subKey") String subKey,
                        @Param("opCount") Long opCount);

    @Select({
            "SELECT * FROM ", TABLE_NAME,
            " WHERE target_id =#{targetId} AND target_type=#{targetType} ",
            " AND sub_key = #{subKey} ",
    })
    public Inventory getInventory(@Param("targetType") int targetType,
                                  @Param("targetId") Long targetId,
                                  @Param("subKey") String subKey);

    @Update({
            "UPDATE ", TABLE_NAME, " SET version=version+1 ",
            ", sale_count = sale_count + #{opCount}, utime=#{utime}",
            " WHERE target_id =#{targetId} AND target_type=#{targetType} ",
            " AND sub_key = #{subKey} ",
            " AND total_count >= sale_count + #{opCount} ",
    })
    public int decrement(@Param("targetType") int targetType,
                         @Param("targetId") Long targetId,
                         @Param("subKey") String subKey,
                         @Param("opCount") Long opCount,
                         @Param("utime") long utime);


    @Update({
            "UPDATE ", TABLE_NAME, " SET version=version+1 ",
            ", sale_count = sale_count - #{opCount}, utime=#{utime}",
            " WHERE target_id =#{targetId} AND target_type=#{targetType} ",
            " AND sub_key = #{subKey} ",
            " AND sale_count >= #{opCount} ",
    })
    public int rollback(@Param("targetType") int targetType,
                        @Param("targetId") long targetId,
                        @Param("subKey") String subKey,
                        @Param("opCount") long opCount,
                        @Param("utime") long utime);
}