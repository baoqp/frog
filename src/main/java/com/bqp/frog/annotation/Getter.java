package com.bqp.frog.annotation;

import com.bqp.frog.invoker.GetterFunction;

import java.lang.annotation.*;

/**
 * @author ash
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Getter {

    Class<? extends GetterFunction<?, ?>> value();

}
