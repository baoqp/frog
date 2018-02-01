package com.bqp.frog.annotation;

import java.lang.annotation.*;

/**
 * 指定property对应的column
 *
 * @author ash
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Column {
    String value();
}