package com.bqp.frog.datasource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 主从数据源
 * @author Bao Qingping
 */
public class MasterSlaveDataSourceWrapper {

    private DataSource masterDataSource;

    private List<DataSource> slaveDataSources = new ArrayList<>();

    private final Random random = new Random();

    // TODO 只支持一主多备
    public MasterSlaveDataSourceWrapper(DataSource... dataSources) {
        assert dataSources.length > 0;

        masterDataSource = dataSources[0];
        for (int i = 1; i < dataSources.length; i++) {
            slaveDataSources.add(dataSources[i]);
        }
    }


    public DataSource getMasterDataSource() {
        return masterDataSource;
    }

    public DataSource getSlaveDataSource() {

        if (slaveDataSources.size() == 0) {
            return masterDataSource;
        }

        return slaveDataSources.get(random.nextInt(slaveDataSources.size()));
    }


    public void setMasterDataSource(DataSource masterDataSource) {
        this.masterDataSource = masterDataSource;
    }

    public List<DataSource> getSlaveDataSources() {
        return slaveDataSources;
    }

    public void setSlaveDataSources(List<DataSource> slaveDataSources) {
        this.slaveDataSources = slaveDataSources;
    }
}
