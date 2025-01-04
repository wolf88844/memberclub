/**
 * @(#)MemberPerformHisDao.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.MemberSubOrder;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author 掘金五阳
 */
@Mapper
public interface MemberSubOrderDao extends BaseMapper<MemberSubOrder> {

    public static final String TABLE_NAME = "member_sub_order";

    Integer insertIgnoreBatch(List<MemberSubOrder> hisList);

    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId}")
    public List<MemberSubOrder> selectByUserId(@Param("userId") long userId);

    @QueryMaster
    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId} AND trade_id=#{tradeId} AND sku_id=#{skuId}")
    public MemberSubOrder selectBySkuId(@Param("userId") long userId,
                                        @Param("tradeId") String tradeId,
                                        @Param("skuId") long skuId);

    @QueryMaster
    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId} AND trade_id=#{tradeId} ")
    public List<MemberSubOrder> selectByTradeId(@Param("userId") long userId,
                                                @Param("tradeId") String tradeId);


    @Update({"UPDATE " + TABLE_NAME + " SET  perform_status=#{toPerformStatus}, utime=#{utime} "
            , " WHERE user_id = #{userId} AND trade_id =#{tradeId} AND sku_id =#{skuId} AND status<#{toPerformStatus}"})
    public int updateStatus(@Param("userId") long userId,
                            @Param("tradeId") String tradeId,
                            @Param("skuId") long skuId,
                            @Param("toPerformStatus") int toPerformStatus,
                            @Param("utime") long utime);


    @Update({
            "UPDATE ", TABLE_NAME,
            " SET status=#{status}, extra=#{extra}, utime=#{utime}, order_id=#{orderId}",
            " ,actprice_fen=#{actPriceFen} WHERE user_id=#{userId} AND sub_trade_id=#{subTradeId} "
    })
    public int updateStatusOnSubmitSuccess(
            @Param("userId") long userId,
            @Param("subTradeId") Long subTradeId,
            @Param("status") Integer status,
            @Param("orderId") String orderId,
            @Param("actPriceFen") Integer actPriceFen,
            @Param("extra") String extra,
            @Param("utime") long utime
    );
}