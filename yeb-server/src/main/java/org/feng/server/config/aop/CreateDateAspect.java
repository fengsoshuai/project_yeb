package org.feng.server.config.aop;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.feng.server.config.annotation.BeanHasCreateDate;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

/**
 * 对有createDate属性的实体进行apo操作
 * @author FengJinSong
 */
@Slf4j
@Aspect
@Component
public class CreateDateAspect {

    /**
     * 切入点：controller包中的add*方法
     */
    @Pointcut("execution(public * org.feng.server.controller.*.add*(..))")
    public void execute(){}

    /**
     * 前置执行
     */
    @Before("execute()")
    public void beforeExecute(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        if(ArrayUtil.isEmpty(args)){
            throw new IllegalArgumentException("增加的数据不能为空");
        }

        Class<?> beanClass = args[0].getClass();
        // 当实体bean上有 BeanHasCreateDate 注解时
        if(beanClass.isAnnotationPresent(BeanHasCreateDate.class)){
            try {
                Method setCreateDate = beanClass.getMethod("setCreateDate", LocalDateTime.class);
                setCreateDate.invoke(args[0], LocalDateTime.now());
            } catch (NoSuchMethodException e) {
                log.error("bean.setCreateDate() is not exist");
                e.printStackTrace();
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }
    }
}
