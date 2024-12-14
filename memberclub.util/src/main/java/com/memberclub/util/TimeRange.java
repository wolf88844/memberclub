/**
 * @(#)TimeRange.java, 十二月 14, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.util;

/**
 * @author yuhaiqiang
 */
public class TimeRange {
    private long stime;

    private long etime;

    public TimeRange(long stime, long etime) {
        this.stime = stime;
        this.etime = etime;
    }

    public long getEtime() {
        return etime;
    }

    public TimeRange setEtime(long etime) {
        this.etime = etime;
        return this;
    }

    public long getStime() {
        return stime;
    }

    public TimeRange setStime(long stime) {
        this.stime = stime;
        return this;
    }

    @Override
    public String toString() {
        return String.format("%s to %s", TimeUtil.format(stime), TimeUtil.format(etime));
    }
}