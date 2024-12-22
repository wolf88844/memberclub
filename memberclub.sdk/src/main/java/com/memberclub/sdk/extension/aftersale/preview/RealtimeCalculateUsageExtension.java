/**
 * @(#)RealtimeCalculateUsageExtension.java, 十二月 22, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.extension.aftersale.preview;

import com.memberclub.common.extension.BaseExtension;
import com.memberclub.domain.dataobject.aftersale.ItemUsage;
import com.memberclub.domain.dataobject.aftersale.preview.AftersalePreviewContext;

import java.util.Map;

/**
 * @author yuhaiqiang
 */
public interface RealtimeCalculateUsageExtension extends BaseExtension {

    public Map<String, ItemUsage> calculateItemUsage(AftersalePreviewContext context);


}