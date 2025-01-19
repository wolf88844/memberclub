/**
 * @(#)SkuDomainService.java, 一月 04, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.sku.service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.memberclub.domain.entity.sku.MemberSku;
import com.memberclub.infrastructure.mybatis.mappers.sku.MemberSkuDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * author: 掘金五阳
 */
@DS("skuDataSource")
@Service
public class SkuDomainService {

    @Autowired
    private MemberSkuDao memberSkuDao;

    public static final Logger LOG = LoggerFactory.getLogger(SkuDomainService.class);


    @Transactional(rollbackFor = Exception.class)
    public void createMemberSku(MemberSku sku) {
        int cnt = memberSkuDao.insert(sku);
    }
}