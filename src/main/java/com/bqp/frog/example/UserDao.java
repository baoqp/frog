package com.bqp.frog.example;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.annotation.ReturnGeneratedId;
import com.bqp.frog.annotation.SQL;

import java.util.List;

@DB
public interface UserDao {
    @SQL("select * from user where age =  :age ")
    List<User> getUsers(int age);

    @ReturnGeneratedId
    @SQL("insert into user(name, age) values(:user.name, :user.age)")
    int save(User user);

    @SQL("select * from user where age in :ages ")
    List<User> getUsers(List<Integer> ages);

    @SQL("update user set age = :age where id = :id ")
    void update(int id, int age);
}