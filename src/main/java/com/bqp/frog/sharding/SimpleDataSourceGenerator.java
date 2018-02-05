package com.bqp.frog.sharding;

import com.bqp.frog.datasource.DataSourceType;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.operator.InvocationContext;

import javax.sql.DataSource;

/**
 * @author Bao Qingping
 */
public class SimpleDataSourceGenerator implements DataSourceGenerator {

    MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper;

    DataSourceType dataSourceType;

    public SimpleDataSourceGenerator(MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper, DataSourceType dataSourceType) {
        this.masterSlaveDataSourceWrapper = masterSlaveDataSourceWrapper;
        this.dataSourceType = dataSourceType;
    }

    @Override
    public DataSource getDataSource(InvocationContext context, Class<?> daoClass) {
        DataSource ds = dataSourceType == DataSourceType.MASTER ?
                masterSlaveDataSourceWrapper.getMasterDataSource() :
                masterSlaveDataSourceWrapper.getSlaveDataSource();
        return ds;
    }
}
