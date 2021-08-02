package com.example.parametricdynamicdatasource.annotation;

import com.example.parametricdynamicdatasource.constants.DataSourceConstants;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义数据源注解
 * @author qzz
 */
@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DS {

    /**
     * 数据源名称
     * @return
     */
    String value() default DataSourceConstants.DS_KEY_MASTER;
}
