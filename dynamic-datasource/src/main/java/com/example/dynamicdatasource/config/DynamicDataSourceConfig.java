package com.example.dynamicdatasource.config;

import com.example.dynamicdatasource.constants.DataSourceConstants;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 动态数据源配置
 * 注解 PropertySource:指定配置信息文件
 * 注解 ConfigurationProperties:指定主从配置前缀
 * @author qzz
 */
//添加此配置（排除 DataSourceAutoConfiguration 的自动配置），否则 报`The dependencies of some of the beans in the application context form a cycle`
@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
@Configuration
@PropertySource("classpath:jdbc.properties")
@MapperScan(basePackages = "com.example.dynamicdatasource.mapper")
public class DynamicDataSourceConfig {


    /**
     * 主数据源
     * @return
     */
    @Bean(DataSourceConstants.DS_KEY_MASTER)
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     *从数据源
     * @return
     */
    @Bean(DataSourceConstants.DS_KEY_SLAVE)
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 设置动态数据源为主数据源
     * 使用注解 Primary 优先从动态数据源中获取
     * @return
     */
    @Bean
    @Primary
    public DataSource dynamicDataSource(){
        Map<Object,Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(DataSourceConstants.DS_KEY_MASTER,masterDataSource());
        dataSourceMap.put(DataSourceConstants.DS_KEY_SLAVE,slaveDataSource());
        //设置动态数据源
        DynamicDataSource dynamicDataSource = new DynamicDataSource();
        dynamicDataSource.setTargetDataSources(dataSourceMap);
        dynamicDataSource.setDefaultTargetDataSource(masterDataSource());

        return dynamicDataSource;
    }
}
