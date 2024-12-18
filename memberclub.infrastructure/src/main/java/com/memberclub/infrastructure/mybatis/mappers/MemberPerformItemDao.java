/**
 * @(#)MemberPerformItemDao.java, 十二月 17, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.MemberPerformItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Mapper
public interface MemberPerformItemDao extends BaseMapper<MemberPerformItem> {

    Integer insertIgnoreBatch(List<MemberPerformItem> items);

}