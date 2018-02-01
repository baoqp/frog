package com.bqp.frog.annotation;


import java.lang.annotation.*;


/**
 * 自定义数据库表字段到类属性的映射
 *
 * @author ash
 * @see Results
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Result {

    /**
     * 数据库列
     */
    String column();

    /**
     * 对象属性
     */
    String property();

}
