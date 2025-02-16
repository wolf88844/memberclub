/**
 * @(#)MemberSkuDao.java, 一月 18, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.mybatis.mappers.sku;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.memberclub.domain.entity.sku.MemberSku;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * author: 掘金五阳
 */
@DS("skuDataSource")
@Mapper
public interface MemberSkuDao extends BaseMapper<MemberSku> {

    Integer insertIgnoreBatch(List<MemberSku> skus);
}