package com.bqp.frog.annotation;

import com.bqp.frog.invoker.SetterFunction;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Setter {

    Class<? extends SetterFunction<?, ?>> value();

}
