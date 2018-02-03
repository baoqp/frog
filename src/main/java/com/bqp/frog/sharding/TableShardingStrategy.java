package com.bqp.frog.sharding;

/**
 * 分表策略
 *
 * @author ash
 */
public interface TableShardingStrategy<T> {

    /**
     * 获得分表后的表名
     *
     * @param table
     * @param shardingParameter
     * @return
     */
    String getTargetTable(String table, T shardingParameter);

}
