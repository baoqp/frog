package com.bqp.frog.example;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;


public class Main {

    public static void main(String[] args) throws Exception {

        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://localhost:3306/Demo");
        config.setUsername("root");
        config.setPassword("");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        HikariDataSource ds = new HikariDataSource(config);


       /* Method method = userDao.getDeclaredMethod("getUsers", int.class);
        BaseOperator operator = getOperator(userDao, method);
        operator.setDataSource(ds);
        operator.setJdbcOperations(new JdbcTemplate());
        Object[] arguments = {20};
        System.out.println(operator.execute(arguments));*/


       /* Method method = userDao.getDeclaredMethod("save", User.class);
        BaseOperator  operator = getOperator(userDao, method);
        operator.setDataSource(ds);
        operator.setJdbcOperations(new JdbcTemplate());
        User user = new User("Han Meimei", 18);
        Object[] arguments2 = {user};
        System.out.println(operator.execute(arguments2));*/

        /*Method method = userDao.getDeclaredMethod("getUsers", List.class);
        BaseOperator operator = getOperator(userDao, method);
        operator.setDataSource(ds);
        operator.setJdbcOperations(new JdbcTemplate());
        List<Integer> ages = new ArrayList<>();
        ages.add(18); ages.add(20);
        Object[] arguments = {ages};
        System.out.println(operator.execute(arguments));*/


        /*Method method = userDao.getDeclaredMethod("update", int.class, int.class);
        BaseOperator operator = getOperator(userDao, method);
        operator.setDataSource(ds);
        operator.setJdbcOperations(new JdbcTemplate());
        Object[] arguments = {3, 18};
        System.out.println(operator.execute(arguments));*/
    }
}
