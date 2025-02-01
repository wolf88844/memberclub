/**
 * @(#)MemberShipDao.java, 二月 01, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers.trade;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.trade.MemberShip;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author: 掘金五阳
 */
@DS("tradeDataSource")
@Mapper
public interface MemberShipDao extends BaseMapper<MemberShip> {

    public static final String TABLE = "member_ship";

    Integer insertIgnoreBatch(List<MemberShip> ships);
}