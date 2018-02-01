package com.bqp.frog.example;

import com.bqp.frog.operator.Operator;
import com.bqp.frog.operator.QueryOperator;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.bqp.frog.operator.Frog.getOperator;


public class Main {

    public static void main(String[] args) throws Exception {

        Class userDao = UserDao.class;

        Method method = userDao.getDeclaredMethod("getUsers", int.class);

        QueryOperator operator = getOperator(userDao, method);


        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Demo");
        config.setUsername("root");
        config.setPassword("apple123");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");

        HikariDataSource ds = new HikariDataSource(config);

        operator.setDataSource(ds);


        Object[] arguments = {18};

        operator.execute(arguments);

    }
}
