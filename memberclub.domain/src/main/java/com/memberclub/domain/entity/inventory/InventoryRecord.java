/**
 * @(#)InventoryRecord.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.entity.inventory;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class InventoryRecord {

    @TableId(type = IdType.AUTO)
    private Long id;

    private int bizType;

    private long userId;

    private String inventoryKey;

    private String operateKey;

    private long targetId;

    private int targetType;

    private String subKey;

    private long opCount;

    private int opType;

    private long utime;

    private long ctime;
}