/**
 * @(#)InventoryOpResponse.java, 一月 29, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.inventory;

import lombok.Data;

/**
 * author: 掘金五阳
 */
@Data
public class InventoryOpResponse {

    private boolean success;

    private boolean needRetry;

    private int errorCode;

    private String msg;

    private Exception e;
}