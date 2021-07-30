package com.example.dynamicdatasource.config;

import com.example.dynamicdatasource.context.DynamicDataSourceContextHolder;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

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
}
