package com.example.parametricdynamicdatasource.proxy;

import com.example.parametricdynamicdatasource.config.DynamicDataSource;
import com.example.parametricdynamicdatasource.context.DynamicDataSourceContextHolder;
import com.example.parametricdynamicdatasource.context.SpringContextHolder;
import com.example.parametricdynamicdatasource.util.DataSourceUtil;
import com.example.parametricdynamicdatasource.vo.DbInfo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 添加JdkParamDsMethodProxy类，实现 InvocationHandler 接口，在 invoke 中编写参数化切换数据源的逻辑即可
 * @author qzz
 */
public class JdkParamDsMethodProxy implements InvocationHandler {

    /**
     *  数据源key
     */
    private String dataSourceKey;
    /**
     * 数据库连接信息
     */
    private DbInfo dbInfo;
    /**
     * 代理目标对象
     */
    private Object targetObject;

    public JdkParamDsMethodProxy(Object targetObject,String dataSourceKey,DbInfo dbInfo){

        this.targetObject = targetObject;
        this.dataSourceKey = dataSourceKey;
        this.dbInfo = dbInfo;
    }


    /**
     * 编写参数化切换数据源的逻辑
     * @param proxy
     * @param method
     * @param args
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("动态代理:"+method.getName());
        //切换数据源
        DataSourceUtil.addDataSourceToDynamic(dataSourceKey,dbInfo);
        DynamicDataSourceContextHolder.setContextKey(dataSourceKey);
        //调用方法
        Object result = method.invoke(targetObject,args);
        DynamicDataSource dynamicDataSource = SpringContextHolder.getContext().getBean(DynamicDataSource.class);
        dynamicDataSource.del(dataSourceKey);
        DynamicDataSourceContextHolder.removeContextKey();
        return result;
    }

    /**
     * 创建代理
     * @return
     */
    public static Object createProxyInstance(Object targetObject, String dataSourceKey,DbInfo dbInfo){
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new JdkParamDsMethodProxy(targetObject, dataSourceKey, dbInfo));
    }
}
