package com.bqp.frog.annotation;

import com.bqp.frog.sharding.DatabaseShardingStrategy;
import com.bqp.frog.sharding.NotUseDatabaseShardingStrategy;
import com.bqp.frog.sharding.NotUseTableShardingStrategy;
import com.bqp.frog.sharding.TableShardingStrategy;

import java.lang.annotation.*;

/**
 * 分库分表注解
 *
 * @author ash
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Sharding {

    Class<? extends TableShardingStrategy> tableShardingStrategy() default NotUseTableShardingStrategy.class;

    Class<? extends DatabaseShardingStrategy> databaseShardingStrategy() default NotUseDatabaseShardingStrategy.class;
}