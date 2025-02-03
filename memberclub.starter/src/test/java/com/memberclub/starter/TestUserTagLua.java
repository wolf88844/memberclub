/**
 * @(#)TestUserTagLua.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 fenbi.com. All rights reserved.
 * FENBI.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.google.common.collect.ImmutableList;
import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.context.usertag.UserTagOpTypeEnum;
import com.memberclub.infrastructure.cache.RedisLuaUtil;
import com.memberclub.starter.mock.MockBaseTest;
import jodd.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

/**
 * author: 掘金五阳
 */
public class TestUserTagLua extends MockBaseTest {

    public static final Logger LOG = LoggerFactory.getLogger(TestUserTagLua.class);

    private static String lua = null;

    static {
        try {
            System.out.println();
            lua = FileUtil.readString(TestUserTagLua.class.getClassLoader().getResource("lua/user_tag_add.lua").getFile());
            System.out.println(lua);

        } catch (Exception e) {
            LOG.error("加载 lua 脚本异常", e);
        }
    }

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    //@Test
    public void test() {
        RedisScript<Long> script = new DefaultRedisScript<>(lua, Long.class);
        UserTagOpCmd cmd = new UserTagOpCmd();
        cmd.setUniqueKey("2");
        cmd.setExpireSeconds(600L);
        cmd.setOpType(UserTagOpTypeEnum.ADD);
        UserTagOpDO tagDO = new UserTagOpDO();
        tagDO.setKey("1_4");
        tagDO.setOpCount(2);
        tagDO.setExpireSeconds(600);
        cmd.setTags(ImmutableList.of(
                tagDO
        ));
        System.out.println(RedisLuaUtil.buildLuaKeys(cmd));

        System.out.println(redisTemplate.execute(script, RedisLuaUtil.buildLuaKeys(cmd)));
    }

}