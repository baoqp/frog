package com.bqp.frog.annotation;

import java.lang.annotation.*;

/**
 * 参数重命名
 *
 * @author ash
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Rename {

  String value();

}