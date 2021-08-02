package com.example.parametricdynamicdatasource.aop;

import com.example.parametricdynamicdatasource.annotation.DS;
import com.example.parametricdynamicdatasource.context.DynamicDataSourceContextHolder;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import java.util.Objects;

/**
 *
 * @author qzz
 */
@Aspect
@Component
public class DynamicDataSourceAspect {


    /**
     * 注解 Pointcut 使用 annotation 指定注解
     */
    @Pointcut("@annotation(com.example.parametricdynamicdatasource.annotation.DS)")
    public void dataSourcePointCut(){

    }

    /**
     * 注解 Around 使用环绕通知处理，使用上下文进行对使用注解 DS 的值进行数据源切换，处理完后，恢复数据源。
     * @param joinPoint
     * @return
     * @throws Throwable
     */
    @Around("dataSourcePointCut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        String dsKey = getDSAnnotation(joinPoint).value();
        //设置数据源
        DynamicDataSourceContextHolder.setContextKey(dsKey);
        try{
            return joinPoint.proceed();
        }finally {
            //恢复数据源
            DynamicDataSourceContextHolder.removeContextKey();
        }
    }

    /**
     * 根据类或方法获取数据源注解
     * @param joinPoint
     * @return
     */
    private DS getDSAnnotation(ProceedingJoinPoint joinPoint) {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        DS dsAnnotation = targetClass.getAnnotation(DS.class);
        // 先判断类的注解，再判断方法注解
        if(Objects.nonNull(dsAnnotation)){
            return dsAnnotation;
        }else{
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            return methodSignature.getMethod().getAnnotation(DS.class);
        }
    }
}
