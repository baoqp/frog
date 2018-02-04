package com.bqp.frog.sharding;

import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.operator.InvocationContext;

import javax.sql.DataSource;


public class ShardedDataSourceGenerator implements DataSourceGenerator {

    DataSourceGroup dataSourceGroup;

    BindingParameterInvoker parameterInvoker;

    DatabaseShardingStrategy strategy;

    public ShardedDataSourceGenerator(DataSourceGroup dataSourceGroup, BindingParameterInvoker parameterInvoker,
                                      DatabaseShardingStrategy strategy) {
        this.dataSourceGroup = dataSourceGroup;
        this.parameterInvoker = parameterInvoker;
        this.strategy = strategy;
    }

    @Override
    public DataSource getDataSource(InvocationContext context, Class<?> daoClass) {
        return null;
    }
}
