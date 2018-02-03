package com.bqp.frog.annotation;

import java.lang.annotation.*;

/**
 * 用此注解修饰的方法参数或参数中的某个属性将被作为参数传入
 *
 * @author ash
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DatabaseShardingBy {

    /**
     * 如果value等于""，直接取被修饰的参数<br>
     * 如果value不等于""，取被修饰参数的value属性
     *
     * @return
     */
    String value() default "";

}