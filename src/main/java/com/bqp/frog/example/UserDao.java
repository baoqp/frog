package com.bqp.frog.example;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.annotation.SQL;

import java.util.List;

@DB
public interface UserDao {
    @SQL("select * from user where age = :age ")
    List<User> getUsers(int age);
}