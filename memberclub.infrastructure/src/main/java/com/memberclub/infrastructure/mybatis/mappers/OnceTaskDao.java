/**
 * @(#)OnceTaskDao.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.OnceTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Mapper
public interface OnceTaskDao extends BaseMapper<OnceTask> {

    static final String TABLE_NAME = "once_task";

    Integer insertIgnoreBatch(List<OnceTask> tasks);

    @QueryMaster
    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} AND task_token IN ",
            "<foreach collection='taskTokens' item='taskToken' separator=',' open='(' close=')'> ",
            " #{taskToken} ",
            "</foreach>",
            "</script>"})
    public List<OnceTask> queryTasks(@Param("userId") long userId, @Param("taskTokens") List<String> taskTokens);

    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} ",
            "</script>"})
    public List<OnceTask> queryTasksByUserId(@Param("userId") long userId);

    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} AND task_group_id = #{taskGroupId}",
            "</script>"})
    public List<OnceTask> queryTasksByUserIdAndGroupId(@Param("userId") long userId, @Param("taskGroupId") String taskGroupId);
}