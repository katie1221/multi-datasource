package com.example.parametricdynamicdatasource.util;

import com.example.parametricdynamicdatasource.config.DynamicDataSource;
import com.example.parametricdynamicdatasource.context.SpringContextHolder;
import com.example.parametricdynamicdatasource.vo.DbInfo;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;

/**
 * 数据源操作工具:通过参数添加数据源，需要根据参数构造数据源，然后添加到前面说的 Map 中
 * @author qzz
 */
public class DataSourceUtil {


    /**
     * 创建新的数据源
     * @param dbInfo
     * @return
     */
    public static DataSource  mackNewDataSource(DbInfo dbInfo){

        String url = "jdbc:mysql://"+dbInfo.getIp()+":"+dbInfo.getPort()+"/"+dbInfo.getDbName()+"?useSSL=false&serverTimezone=GMT%2B8&characterEncoding=UTF-8";
        String driverClassName = ObjectUtils.isEmpty(dbInfo.getDriverClassName())?"com.mysql.jdbc.Driver":dbInfo.getDriverClassName();
        return DataSourceBuilder.create()
                .url(url)
                .driverClassName(driverClassName)
                .username(dbInfo.getUserName())
                .password(dbInfo.getPassword())
                .build();
    }

    /**
     * 添加数据源到动态源中
     * @param key
     * @param dataSource
     */
    public static void addDataSourceToDynamic(String key,DataSource dataSource){
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.addDataSource(key, dataSource);
    }

    /**
     * 根据数据库连接信息添加数据源到动态中
     * @param key
     * @param dbInfo
     */
    public static void addDataSourceToDynamic(String key,DbInfo dbInfo){
        //创建新的数据源
        DataSource dataSource = mackNewDataSource(dbInfo);
        //添加数据源到动态源中
        addDataSourceToDynamic(key, dataSource);
    }
}
