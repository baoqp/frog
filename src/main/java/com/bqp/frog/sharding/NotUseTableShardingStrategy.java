package com.bqp.frog.sharding;

public final class NotUseTableShardingStrategy implements TableShardingStrategy {

    @Override
    public String getTargetTable(String table, Object shardingParameter) {
        throw new UnsupportedOperationException("error, unreachable code");
    }

}
