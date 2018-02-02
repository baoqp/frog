package com.bqp.frog.annotation;

import java.lang.annotation.*;

/**
 * 用于修饰insert语句，标识方法返回自增id
 *
 * @author ash
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ReturnGeneratedId {
}
