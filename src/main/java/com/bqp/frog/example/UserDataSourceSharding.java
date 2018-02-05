package com.bqp.frog.example;

import com.bqp.frog.sharding.DatabaseShardingStrategy;

public class UserDataSourceSharding implements DatabaseShardingStrategy<Integer> {

    @Override
    public String getDataSourceFactoryName(Integer age) {
        if (age < 18) {
            return "ds1";
        }
        return "ds2";
    }
}