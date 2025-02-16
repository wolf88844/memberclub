/**
 * @(#)TestDemo.java, 一月 27, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.memberclub.common.util.JsonUtils;
import org.junit.Test;

/**
 * author: 掘金五阳
 */
public class TestOnceTask {
    static final String TABLE_NAME = "once_task";


    @Test
    public void test1() {
    }

    @Test
    public void test() {
        String[] msgs = {"<script> SELECT * FROM ", "once_task",
                " WHERE biz_type=#{bizType} AND id>#{minId} ",
                " AND stime >= #{minStime} AND stime<= #{maxStime} ",
                " ORDER BY id",
                " LIMIT #{pageSize}",
                "</script>"};

        String str = "<script> SELECT * FROM " + TABLE_NAME +
                " WHERE biz_type=#{bizType} AND id>#{minId} " +
                " AND stime >= #{minStime} AND stime <= #{maxStime}" +
                " ORDER BY id" +
                " LIMIT #{pageSize}" +
                "</script>";
        System.out.println(str);
        System.out.println(str.substring(112));
    }
}