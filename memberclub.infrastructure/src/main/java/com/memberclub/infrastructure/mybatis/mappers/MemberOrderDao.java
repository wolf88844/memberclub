/**
 * @(#)MemberOrderDao.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.MemberOrder;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author yuhaiqiang
 */
public interface MemberOrderDao extends BaseMapper<MemberOrder> {

    static final String TABLE_NAME = "member_order";

    @Update({"<SCRIPT>UPDATE ", TABLE_NAME,
            " SET status=#{toStatus}, utime=#{utime} ",
            "WHERE user_id=#{userId} AND trade_id=#{tradeId} AND status IN ",
            "<foreach collection='fromStatuses' item='status' separator=',' open='(' close=')'> ",
            " #{status} ",
            "</foreach>",
            "</SCRIPT>"})
    public int updateStatus(@Param("user_id") long userId,
                            @Param("tradeId") String tradeId,
                            @Param("toStatus") int toStatus,
                            @Param("fromStatuses") List<Integer> fromStatuses);

    @QueryMaster
    @Select({"SELECT * FROM ", TABLE_NAME,
            " WHERE user_id=#{userId} AND trade_id=#{tradeId} "})
    public MemberOrder selectByTradeId(@Param("user_id") long userId,
                                       @Param("tradeId") String tradeId);


}