/**
 * @(#)SkuPerformItemConfig.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject;

import lombok.Data;

/**
 * @author yuhaiqiang
 */
@Data
public class SkuItemPerformConfigDO {

    private int bizType;

    private int rightType;

    private int rightId;

    private int assetCount;

    private int periodCount;

    private int periodType;

    private int cycle;
}