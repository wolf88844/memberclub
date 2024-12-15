/**
 * @(#)Monitor.java, 十二月 15, 2024.
 * <p>
 * Copyright 2024 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.common.monitor;

/**
 * @author yuhaiqiang
 */
public enum Monitor {

    PERFORM("perform");


    private String name;

    Monitor(String name) {
        this.name = name;
    }
    
    public void report(int bizType, String line) {
        MonitorUtil.monitor(String.format("%s_%s", name, bizType), line);
    }

    @Override
    public String toString() {
        return this.name;
    }

}
