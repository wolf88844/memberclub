/**
 * @(#)BizUtils.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.uti;

/**
 * author: 掘金五阳
 */
public class BizUtils {

    public static String toItemToken(Long hisToken, long rightId, int buyIndex, int phase) {
        return String.format("%s_%s_%s_%s", hisToken, rightId, buyIndex, phase);
    }
}