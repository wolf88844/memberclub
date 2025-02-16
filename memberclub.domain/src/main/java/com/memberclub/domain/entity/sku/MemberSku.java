/**
 * @(#)MemberSku.java, 一月 18, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity.sku;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class MemberSku {

    private Long id;

    private Integer bizType;

    private Integer status;

    private String saleInfo;

    private String financeInfo;

    private String viewInfo;

    private String restrictInfo;

    private String inventoryInfo;

    private String performanceInfo;

    private String extra;

    private Long utime;

    private Long ctime;
}