package com.example.dynamicdatasource.context;

import com.example.dynamicdatasource.constants.DataSourceConstants;

/**
 * 动态数据源名称上下文处理：动态选择数据源
 * @author qzz
 */
public class DynamicDataSourceContextHolder {

    /**
     * 动态数据源名称上下文
     */
    private static final ThreadLocal<String> DATASOURCE_CONTEXT_KEY_HOLDER = new ThreadLocal<>();

    /**
     * 设置/切换数据源
     * @param key
     */
    public static void setContextKey(String key){
        System.out.println("切换数据源"+key);
        DATASOURCE_CONTEXT_KEY_HOLDER.set(key);
    }

    /**
     * 获取数据源
     */
    public static String getContextKey(){
        String key = DATASOURCE_CONTEXT_KEY_HOLDER.get();
        if(key == null){
            //为空，取默认数据源
            return DataSourceConstants.DS_KEY_MASTER;
        }else{
            return key;
        }
    }

    /**
     * 删除当前数据源名称
     */
    public static void removeContextKey(){
        DATASOURCE_CONTEXT_KEY_HOLDER.remove();
    }
}
