package com.bqp.frog.sharding;

public final class NotUseDatabaseShardingStrategy implements DatabaseShardingStrategy {

    @Override
    public String getDataSourceFactoryName(Object shardingParameter) {
        throw new UnsupportedOperationException("error, unreachable code");
    }

}
