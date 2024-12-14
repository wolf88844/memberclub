/**
 * @(#)MemberPerformHisDao.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.MemberPerformHis;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author 掘金五阳
 */
@Mapper
public interface MemberPerformHisDao extends BaseMapper<MemberPerformHis> {

    public static final String TABLE_NAME = "member_perform_his";

    @Select("SELECT * FROM " + TABLE_NAME + " WHERE user_id = #{userId}")
    public List<MemberPerformHis> selectByUserId(@Param("userId") long userId);
}