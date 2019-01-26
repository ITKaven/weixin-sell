package com.kaven.weixinsell.cloudDB;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class cloudDBConnectionTest {

    @Test
    public void test() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = (Connection)DriverManager.getConnection(
                "jdbc:mysql://47.112.7.219:3306/sell?characterEncoding=UTF8&useSSL=false",
                "root2","015036");
    }

}
