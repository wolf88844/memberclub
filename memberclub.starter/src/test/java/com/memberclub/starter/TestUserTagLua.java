/**
 * @(#)TestUserTagLua.java, 一月 30, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter;

import com.fasterxml.jackson.core.type.TypeReference;
import com.google.common.collect.ImmutableList;
import com.memberclub.common.retry.RetryUtil;
import com.memberclub.common.util.JsonUtils;
import com.memberclub.domain.context.usertag.UserTagDO;
import com.memberclub.domain.context.usertag.UserTagOpCmd;
import com.memberclub.domain.context.usertag.UserTagOpDO;
import com.memberclub.domain.context.usertag.UserTagOpTypeEnum;
import com.memberclub.infrastructure.cache.RedisLuaUtil;
import com.memberclub.starter.mock.MockBaseTest;
import jodd.io.FileUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;

import java.util.List;

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



    @Test
    public  void testJson(){
        List<UserTagDO> tags= Lists.newArrayList();
        UserTagDO tag =new UserTagDO();
        tag.setKey("hello");
        tag.setCount(22L);
        tags.add(tag);

        System.out.println(RetryUtil.toJson(tags));
        System.out.println();

        String json2 = RetryUtil.toJson(tags);
        String json = JsonUtils.toJson(tags);
        List<UserTagDO> userTagDOS=   JsonUtils.fromJson(json, new TypeReference<List<UserTagDO>>() {
        });

        List<UserTagDO> usertagDos2 = RetryUtil.fromJson(json2, List.class);

        System.out.println(usertagDos2);

    }

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