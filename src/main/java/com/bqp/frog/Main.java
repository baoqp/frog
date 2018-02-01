package com.bqp.frog;

import com.bqp.frog.annotation.DB;
import com.bqp.frog.annotation.SQL;
import com.bqp.frog.operator.Operator;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static com.bqp.frog.operator.Frog.getOperator;


public class Main {

    public static class User {
        private String address;


        private String name;

        private int age;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }

    @DB
    public static interface UserDao {
        @SQL("select * from user where address in :addressList and age = :age ")
        List<User> getUser(int age, List<String> addressList);
    }

    public static void main(String[] args) throws Exception {

        Class userDao = UserDao.class;

        Method method = userDao.getDeclaredMethod("getUser", int.class, List.class);

        Operator operator = getOperator(userDao, method);

        List<String> addressList = new ArrayList<>();
        addressList.add("ab");
        addressList.add("bc");
        Object[] arguments = {18, addressList};

        operator.execute(arguments);

    }
}
