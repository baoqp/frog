package com.bqp.frog.annotation;

import com.bqp.frog.jdbc.RowMapper;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Mapper {

    // 用于指定RowMapper做resultSet的抽取
    Class<? extends RowMapper<?>> value();

}
