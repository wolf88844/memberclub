/**
 * @(#)MetricsEnum.java, 十二月 28, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.sdk.common;

import com.memberclub.domain.common.BizTypeEnum;
import io.micrometer.core.instrument.Metrics;
import org.apache.commons.lang.ObjectUtils;

/**
 * @author yuhaiqiang
 */
public enum Monitor {

    PERFORM("perform_request"),
    PERFORM_EXECUTE("perform_execute"),
    ;

    private String name;

    Monitor(String name) {
        this.name = name;
    }

    public void counter(int bizType, Object... tags) {
        String[] tagStrs = new String[tags.length];
        for (int i = 0; i < tags.length; i++) {
            tagStrs[i] = ObjectUtils.defaultIfNull(tags[i], "null").toString();
        }
        String counterName = buildCounterName(bizType);
        Metrics.counter(counterName, tagStrs);
    }

    private String buildCounterName(int bizType) {
        return String.format("%s_%s_%s", name, "counter", bizType);
    }

    public void counter(BizTypeEnum bizType, Object... tags) {
        counter(bizType.toBizType(), tags);
    }
}
