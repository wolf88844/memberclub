/**
 * @(#)DataSourceConfiguration.java, 二月 02, 2025.
 * <p>
 * Copyright 2025 memberclub.com. All rights reserved.
 * memberclub.COM PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.memberclub.starter.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import com.baomidou.dynamic.datasource.provider.AbstractDataSourceProvider;
import com.baomidou.dynamic.datasource.provider.DynamicDataSourceProvider;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DataSourceProperty;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceAutoConfiguration;
import com.baomidou.dynamic.datasource.spring.boot.autoconfigure.DynamicDataSourceProperties;
import org.apache.shardingsphere.driver.jdbc.adapter.AbstractDataSourceAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Primary;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * author: 掘金五阳
 */
@Configuration
@AutoConfigureBefore({DynamicDataSourceAutoConfiguration.class,
        SpringBootConfiguration.class})
@ConditionalOnProperty(name = "spring.shardingsphere.enabled", havingValue = "true", matchIfMissing = false)
public class DataSourceConfiguration {

    // 分表数据源名称
    private static final String SHARDING_DATA_SOURCE_NAME = "sharding";

    //mybatisplus 动态数据源配置项
    @Autowired
    private DynamicDataSourceProperties properties;

    // shardingjdbc的数据源
    @Lazy
    @Resource(name = "shardingSphereDataSource")
    AbstractDataSourceAdapter shardingSphereDataSource;

    @Lazy
    @Resource(name = "dataSource")
    private DataSource dataSource;

    // 动态数据源提供者bean注册
    @Primary
    @Bean
    public DynamicDataSourceProvider dynamicDataSourceProvider() {
        // 首先从mybatisplus的动态数据源先获取
        Map<String, DataSourceProperty> datasourceMap = properties.getDatasource();
        return new AbstractDataSourceProvider() {
            @Override
            public Map<String, DataSource> loadDataSources() {
                Map<String, DataSource> dataSourceMap = createDataSourceMap(datasourceMap);
                // 然后将 shardingjdbc 管理的数据源也交给动态数据源管理
                dataSourceMap.put("tradeDataSource", shardingSphereDataSource);
                dataSourceMap.put("skuDataSource", shardingSphereDataSource);
                return dataSourceMap;
            }
        };
    }


    // 数据源配置 @Primary 代表优先使用该bean
    @Primary
    @Bean
    public DataSource dataSource(DynamicDataSourceProvider dynamicDataSourceProvider) {
        DynamicRoutingDataSource dataSource = new DynamicRoutingDataSource();
//        首选数据源
        dataSource.setPrimary(properties.getPrimary());
        dataSource.setStrict(properties.getStrict());
        dataSource.setStrategy(properties.getStrategy());
//        配置数据源提供者
        dataSource.setP6spy(properties.getP6spy());
        dataSource.setSeata(properties.getSeata());
        return dataSource;
    }
}