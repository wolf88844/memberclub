/**
 * @(#)OnceTaskDao.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers.trade;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.common.annotation.QueryMaster;
import com.memberclub.domain.entity.trade.OnceTask;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Set;

/**
 * author: 掘金五阳
 */
@DS("tradeDataSource")
@Mapper
public interface OnceTaskDao extends BaseMapper<OnceTask> {

    static final String TABLE_NAME = "once_task";

    Integer insertIgnoreBatch(List<OnceTask> tasks);

    @QueryMaster
    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} AND task_type=#{taskType} AND task_token IN ",
            "<foreach collection='taskTokens' item='taskToken' separator=',' open='(' close=')'> ",
            " #{taskToken} ",
            "</foreach>",
            "</script>"})
    public List<OnceTask> queryTasks(@Param("userId") long userId, @Param("taskTokens") List<String> taskTokens,
                                     @Param("taskType") Integer taskType);

    @Select("<script> SELECT * FROM " + "${tableName}" +
            " WHERE biz_type=#{bizType} AND id>#{minId} " +
            " AND task_type =#{taskType} " +
            " AND stime &gt;= #{minStime} AND stime &lt;= #{maxStime}" +
            " <if test='userIds != null'> AND user_id IN " +
            "<foreach collection='userIds' item='userId' separator=',' open='(' close=')'> " +
            " #{userId} " +
            "</foreach> </if>" +
            " <if test='taskGroupIds != null'> AND task_group_id IN " +
            "<foreach collection='taskGroupIds' item='taskGroupId' separator=',' open='(' close=')'> " +
            " #{taskGroupId} " +
            "</foreach> </if>" +
            " AND status IN " +
            "<foreach collection='statuses' item='status' separator=',' open='(' close=')'> " +
            " #{status} " +
            "</foreach>" +
            " ORDER BY id" +
            " LIMIT #{pageSize}" +
            "</script>")
    public List<OnceTask> scanTasks(@Param("tableName") String tableName,
                                    @Param("bizType") Integer bizType,
                                    @Param("userIds") Set<Long> userIds,
                                    @Param("taskGroupIds") Set<String> taskGroupIds,
                                    @Param("minStime") Long minStime,
                                    @Param("maxStime") Long maxStime,
                                    @Param("statuses") Set<Integer> statuses,
                                    @Param("taskType") Integer taskType,
                                    @Param("minId") Long minId,
                                    @Param("pageSize") Integer pageSize);

    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} AND task_type=#{taskType}",
            "</script>"})
    public List<OnceTask> queryTasksByUserId(@Param("userId") long userId,
                                             @Param("taskType") Integer taskType);

    @Select({"<script> SELECT * FROM ", TABLE_NAME,
            "WHERE user_id=#{userId} AND task_group_id = #{taskGroupId} ",
            " AND task_type=#{taskType}",
            "</script>"})
    public List<OnceTask> queryTasksByUserIdAndGroupId(@Param("userId") long userId,
                                                       @Param("taskGroupId") String taskGroupId,
                                                       @Param("taskType") Integer taskType);
}