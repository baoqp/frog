package com.bqp.frog.sharding;

import com.bqp.frog.operator.InvocationContext;

import javax.sql.DataSource;

/**
 * @author ash
 */
public interface DataSourceGenerator {

    DataSource getDataSource(InvocationContext context, Class<?> daoClass);

}
