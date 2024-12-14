/**
 * @(#)PeriodUtils.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.util;

/**
 * @author yuhaiqiang
 */
public class PeriodUtils {

    public static TimeRange buildTimeRangeFromNow(int periodDays) {
        return buildTimeRangeFromNow(periodDays, true);
    }


    public static TimeRange buildTimeRangeFromNow(int periodDays, boolean containsFirstDay) {
        if (containsFirstDay) {
            periodDays = periodDays - 1;//包含当天
        }

        long now = TimeUtil.now();
        long etime = TimeUtil.plusGivenDayEtime(now, periodDays);
        return new TimeRange(now, etime);
    }
}