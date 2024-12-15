/**
 * @(#)MemberPerformHisDao.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.MemberPerformHis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 掘金五阳
 */
@Mapper
public interface MemberPerformHisDao extends BaseMapper<MemberPerformHis> {

    public static final String TABLE_NAME = "member_perform_his";

    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId}")
    public List<MemberPerformHis> selectByUserId(@Param("userId") long userId);

    @QueryMaster
    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId} AND trade_id=#{tradeId} AND sku_id=#{skuId}")
    public MemberPerformHis select(@Param("userId") long userId,
                                   @Param("tradeId") String tradeId,
                                   @Param("skuId") long skuId);

    @Update({"UPDATE " + TABLE_NAME + " SET  status=#{toStatus}, utime=#{utime} "
            , " WHERE user_id = #{userId}AND trade_id =#{tradeId}AND sku_id =#{skuId} AND status<#{toStatus}"})
    public int updateStatus(@Param("userId") long userId,
                            @Param("tradeId") String tradeId,
                            @Param("skuId") long skuId,
                            @Param("toStatus") int status,
                            @Param("utime") long utime);
}