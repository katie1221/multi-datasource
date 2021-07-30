package com.example.basicmultidatasource.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * 数据源配置:有了数据源连接信息，需要把数据源注入到 Spring 中
 *
 * 由于每个数据库使用独立的一套数据库连接，数据库连接使用的 SqlSession 进行会话连接，SqlSession 是由SqlSessionFactory 生成。
 * 因此，需要分别配置SqlSessionFactory
 *
 * 注解 PropertySource:指定配置信息文件
 * 注解 ConfigurationProperties:指定主从配置前缀
 *
 * @author qzz
 * @date 2021/7/29
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
public class DatasourceConfig {


    /**
     * 主数据源
     * @return
     */
    @Bean("master")
    @ConfigurationProperties(prefix = "spring.datasource.master")
    public DataSource masterDataSource(){
        return DataSourceBuilder.create().build();
    }

    /**
     * 从数据源
     * @return
     */
    @Bean("slave")
    @ConfigurationProperties(prefix = "spring.datasource.slave")
    public DataSource slaveDataSource(){
        return DataSourceBuilder.create().build();
    }
}
