/**
 * @(#)ForceDBRouteHintShardingAlgorithm.java, 二月 04, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.config.db;

import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingAlgorithm;
import org.apache.shardingsphere.sharding.api.sharding.hint.HintShardingValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Properties;

/**
 * author: 掘金五阳
 */
public class ForceDBRouteHintShardingAlgorithm implements HintShardingAlgorithm<Integer> {

    @Override
    public void init() {

    }

    @Override
    public String getType() {
        return "hint";
    }

    @Override
    public Properties getProps() {
        return null;
    }

    @Override
    public void setProps(Properties props) {

    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, HintShardingValue<Integer> hintShardingValue) {
        Collection<String> result = new ArrayList<>();
        // 遍历数据源
        for (String actualDb : collection) {
            // hintShardingValue: 代表分片值, 指的是使用者对分片键赋的值
            Collection<Integer> values = hintShardingValue.getValues();
            for (Integer value : values) {
                //直接路由到与我们传入的路由值相等的数据源上
                if (actualDb.endsWith(String.valueOf(value))) {
                    result.add(actualDb);
                }
            }
        }
        return result;
    }
}