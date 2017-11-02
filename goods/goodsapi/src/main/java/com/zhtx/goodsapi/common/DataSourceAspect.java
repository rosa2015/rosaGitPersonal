package com.zhtx.goodsapi.common;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * Created by maqian on 16/4/9.
 */

@Aspect
@Component
public class DataSourceAspect {


    /**
     * * 拦截目标方法，获取由@DataSource指定的数据源标识，设置到线程存储中以便切换数据源
     * *
     * * @param point
     * * @throws Exception
     */


    public void before(JoinPoint point) throws Exception {
        Class<?> target = point.getTarget().getClass();
        MethodSignature signature = (MethodSignature) point.getSignature();
        // 默认使用目标类型的注解，如果没有则使用其实现接口的注解
        for (Class<?> clazz : target.getInterfaces()) {
            resolveDataSource(clazz, signature.getMethod());
        }
        resolveDataSource(target, signature.getMethod());
    }
    public void after(JoinPoint point) throws Exception {
        DBContextHolder.clearDBType();
    }
    /**
     * 提取目标对象方法注解和类型注解中的数据源标识
     *
     * @param clazz
     * @param method
     */
    private void resolveDataSource(Class<?> clazz, Method method) {
        try {
            Class<?>[] types = method.getParameterTypes();
            // 默认使用类型注解
            if (clazz.isAnnotationPresent(DataSourceType.class)) {
                DataSourceType source = clazz.getAnnotation(DataSourceType.class);
                System.out.println("切换数据源--类级别:"+source.value());
                DBContextHolder.setDbType(source.value().name());
            }
            // 方法注解可以覆盖类型注解
            Method m = clazz.getMethod(method.getName(), types);
            if (m != null && m.isAnnotationPresent(DataSourceType.class)) {
                DataSourceType source = m.getAnnotation(DataSourceType.class);
                System.out.println("切换数据源--方法级别:"+source.value());

                DBContextHolder.setDbType(source.value().name());
            }
        } catch (Exception e) {
            System.out.println(clazz + ":" + e.getMessage());
        }
    }
}
