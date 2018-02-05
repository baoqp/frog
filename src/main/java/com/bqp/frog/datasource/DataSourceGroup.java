package com.bqp.frog.datasource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 保存所有分区的库
 *
 * @author Bao Qingping
 */
public class DataSourceGroup {

    private Map<String, MasterSlaveDataSourceWrapper> dataSourceWrapperMap = new HashMap<>();

    public void add(String name, MasterSlaveDataSourceWrapper dataSourceWrapper) {
        dataSourceWrapperMap.put(name, dataSourceWrapper);
    }

    public MasterSlaveDataSourceWrapper get(String name) {
        return dataSourceWrapperMap.get(name);
    }


    public DataSource getMasterDataSource(String name) {
        return dataSourceWrapperMap.get(name).getMasterDataSource();
    }

    public DataSource getSlaveDataSource(String name) {
        return dataSourceWrapperMap.get(name).getSlaveDataSource();
    }

}
