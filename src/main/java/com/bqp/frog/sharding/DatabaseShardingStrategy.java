package com.bqp.frog.sharding;

/**
 * 分库策略
 *
 * @author ash
 */
public interface DatabaseShardingStrategy<T> {

    /**
     * 获得数据源工厂名称
     *
     * @param shardingParameter
     * @return
     */
    String getDataSourceFactoryName(T shardingParameter);

}
