/**
 * @(#)OnceTaskDao.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.OnceTask;

/**
 * author: 掘金五阳
 */
public interface OnceTaskDao extends BaseMapper<OnceTask> {

    static final String TABLE_NAME = "once_task";
}