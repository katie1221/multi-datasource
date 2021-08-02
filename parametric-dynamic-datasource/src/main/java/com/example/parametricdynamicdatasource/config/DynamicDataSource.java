package com.example.parametricdynamicdatasource.config;

import com.example.parametricdynamicdatasource.context.DynamicDataSourceContextHolder;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Map;

/**
 * 动态数据源
 * 继承抽象类 AbstractRoutingDataSource ，需要实现方法 determineCurrentLookupKey，即路由策略
 * @author qzz
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

    /**
     * 路由策略
     * @return
     */
    @Override
    protected Object determineCurrentLookupKey() {
        //获取数据源
        return DynamicDataSourceContextHolder.getContextKey();
    }

    private Map<Object,Object> backupTargetDataSource;

    /**
     * 自定义构造函数
     * @param defaultDataSource
     * @param targetDataSource
     */
    public DynamicDataSource(DataSource defaultDataSource, Map<Object,Object> targetDataSource){
        backupTargetDataSource = targetDataSource;
        super.setDefaultTargetDataSource(defaultDataSource);
        super.setTargetDataSources(targetDataSource);
        super.afterPropertiesSet();
    }

    /**
     * 添加新数据源
     * @param key
     * @param dataSource
     */
    public void addDataSource(String key, DataSource dataSource){
        this.backupTargetDataSource.put(key, dataSource);
        super.setTargetDataSources(this.backupTargetDataSource);
        super.afterPropertiesSet();
    }

    /**
     * 删除数据源
     * @param key
     */
    public void del(String key){
        DataSource dataSource = (DataSource) backupTargetDataSource.get(key);

        try {
            dataSource.getConnection().close();
            ((HikariDataSource)dataSource).close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        backupTargetDataSource.remove(key);
        super.setTargetDataSources(this.backupTargetDataSource);
        super.afterPropertiesSet();
    }
}
