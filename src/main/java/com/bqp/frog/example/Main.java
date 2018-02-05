package com.bqp.frog.example;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.annotation.DatabaseShardingBy;
import com.bqp.frog.annotation.SQL;
import com.bqp.frog.annotation.Sharding;
import com.bqp.frog.datasource.DataSourceGroup;
import com.bqp.frog.datasource.MasterSlaveDataSourceWrapper;
import com.bqp.frog.operator.Frog;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.List;


public class Main {

    @DB
    @Sharding(databaseShardingStrategy = UserDataSourceSharding.class)
    interface UserDao {

        // 插入数据
        @SQL("insert into user(name, age) values(:name, :age)")
        void add(String name, @DatabaseShardingBy int age);

        // 根据name取num的总和
        @SQL("select * from user where age >= :age")
        List<User> getUsers(@DatabaseShardingBy int age);

    }

    public static void main(String[] args) throws Exception {

        HikariConfig masterConfig = new HikariConfig();
        masterConfig.setJdbcUrl("jdbc:mysql://localhost:3306/demo1");
        masterConfig.setUsername("root");
        masterConfig.setPassword("");
        HikariDataSource masterDS = new HikariDataSource(masterConfig);

        HikariConfig slaveConfig = new HikariConfig();
        slaveConfig.setJdbcUrl("jdbc:mysql://localhost:3306/demo2");
        slaveConfig.setUsername("root");
        slaveConfig.setPassword("");
        HikariDataSource slaveDS = new HikariDataSource(slaveConfig);

        MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper = new MasterSlaveDataSourceWrapper(masterDS, slaveDS);


        HikariConfig masterConfig2 = new HikariConfig();
        masterConfig2.setJdbcUrl("jdbc:mysql://localhost:3306/demo3");
        masterConfig2.setUsername("root");
        masterConfig2.setPassword("");
        HikariDataSource masterDS2 = new HikariDataSource(masterConfig2);

        HikariConfig slaveConfig2 = new HikariConfig();
        slaveConfig2.setJdbcUrl("jdbc:mysql://localhost:3306/demo4");
        slaveConfig2.setUsername("root");
        slaveConfig2.setPassword("");
        HikariDataSource slaveDS2 = new HikariDataSource(slaveConfig2);

        MasterSlaveDataSourceWrapper masterSlaveDataSourceWrapper2 = new MasterSlaveDataSourceWrapper(masterDS2, slaveDS2);

        DataSourceGroup dataSourceGroup = new DataSourceGroup();
        dataSourceGroup.add("ds1", masterSlaveDataSourceWrapper);
        dataSourceGroup.add("ds2", masterSlaveDataSourceWrapper2);

        Frog frog = new Frog(dataSourceGroup);

        UserDao userDao = frog.create(UserDao.class);

        userDao.add("Nancy", 8);
        userDao.add("Shelly", 28);

        //List<User> users = userDao.getUsers(18);

        //System.out.println(users);

    }
}
