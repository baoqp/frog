package com.bqp.frog.sharding;

import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.DataSourceType;
import com.bqp.frog.operator.BindingParameterInvoker;
import com.bqp.frog.operator.InvocationContext;

import javax.sql.DataSource;


public class ShardedDataSourceGenerator implements DataSourceGenerator {

    DataSourceGroup dataSourceGroup;

    DataSourceType dataSourceType;

    BindingParameterInvoker parameterInvoker;

    DatabaseShardingStrategy strategy;

    public ShardedDataSourceGenerator(DataSourceGroup dataSourceGroup, DataSourceType dataSourceType,
                                      BindingParameterInvoker parameterInvoker,
                                      DatabaseShardingStrategy strategy) {
        this.dataSourceGroup = dataSourceGroup;
        this.dataSourceType = dataSourceType;
        this.parameterInvoker = parameterInvoker;
        this.strategy = strategy;
    }

    @Override
    public DataSource getDataSource(InvocationContext context, Class<?> daoClass) {
        String dataSourceName = getDataSourceName(context);

        DataSource ds = dataSourceType == DataSourceType.MASTER ?
                dataSourceGroup.getMasterDataSource(dataSourceName) :
                dataSourceGroup.getSlaveDataSource(dataSourceName);
        return ds;
    }


    public String getDataSourceName(InvocationContext context) {
        Object shardParam = context.getBindingValue(parameterInvoker);
        return strategy.getDataSourceFactoryName(shardParam);
    }
}
