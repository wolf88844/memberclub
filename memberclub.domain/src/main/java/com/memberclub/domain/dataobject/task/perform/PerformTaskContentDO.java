/**
 * @(#)PerformTaskContentDO.java, 十二月 29, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.dataobject.task.perform;

import com.memberclub.domain.dataobject.task.TaskContentDO;
import lombok.Data;

import java.util.List;

/**
 * author: 掘金五阳
 */
@Data
public class PerformTaskContentDO extends TaskContentDO {

    private int bizType;

    private String tradeId;

    private String performHisToken;

    private List<PerformTaskContentItemDO> items;
}