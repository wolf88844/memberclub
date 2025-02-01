/**
 * @(#)UserTagLuaCmd.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.infrastructure.usertag;

import com.google.common.collect.Lists;
import com.memberclub.common.util.TimeUtil;
import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpDO;

import java.util.List;

/**
 * author: 掘金五阳
 */
public class UserTagLuaUtil {

    public static List<String> buildLuaKeys(UserTagOpCmd cmd) {
        List<String> params = Lists.newArrayList();
        //params.add(String.valueOf((4 + cmd.getTags().size() * 3)));
        params.add(cmd.getUniqueKey());
        params.add(TimeUtil.now() + "");
        params.add(cmd.getExpireSeconds() + "");
        params.add(cmd.getTags().size() + "");
        for (UserTagOpDO tag : cmd.getTags()) {
            params.add(tag.getKey());
            params.add(tag.getOpCount() + "");
            params.add(tag.getExpireSeconds() + "");
        }
        return params;
    }

    public static List<String> buildDelLuaKeys(UserTagOpCmd cmd) {
        List<String> params = Lists.newArrayList();
        //params.add(String.valueOf((4 + cmd.getTags().size() * 3)));
        params.add(cmd.getUniqueKey());
        params.add(cmd.getTags().size() + "");
        for (UserTagOpDO tag : cmd.getTags()) {
            params.add(tag.getKey());
            params.add(tag.getOpCount() + "");
            //   params.add(tag.getExpireSeconds() + "");
        }
        return params;
    }
}