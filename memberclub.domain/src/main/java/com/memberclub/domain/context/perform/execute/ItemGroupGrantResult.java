/**
 * @(#)ItemGroupGrantResult.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.domain.context.perform.execute;

import lombok.Data;

import java.util.Map;

/**
 * ItemGroupGrantResult 是一个用于表示执行项组授权结果的类。
 * 该类包含一个映射，键为字符串类型，值为 {@link ItemGrantResult} 对象，
 * 用于记录每个执行项的授权结果。通常用于批量授权操作的结果汇总。
 *
 * @author 掘金五阳
 */
@Data
public class ItemGroupGrantResult {

    /**
     * 授权结果映射，键为执行项标识（如子交易ID或其他唯一标识符），值为对应的授权结果。
     */
    private Map<String, ItemGrantResult> grantMap;
}
