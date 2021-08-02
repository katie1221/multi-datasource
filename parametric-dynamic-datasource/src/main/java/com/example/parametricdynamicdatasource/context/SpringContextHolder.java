package com.example.parametricdynamicdatasource.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Spring 上下文工具类
 * @author qzz
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * 设置Spring 上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        SpringContextHolder.applicationContext = applicationContext;
    }

    /**
     * 返回上下文
     * @return
     */
    public static ApplicationContext getContext(){
        return SpringContextHolder.applicationContext;
    }
}
