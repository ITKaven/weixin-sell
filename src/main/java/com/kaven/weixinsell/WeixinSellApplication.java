package com.kaven.weixinsell;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@SpringBootApplication
public class WeixinSellApplication {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.jdbc.Driver");
//        Connection connection = (Connection) DriverManager.getConnection(
//                "jdbc:mysql://47.112.7.219:3306/sell?characterEncoding=UTF8&useSSL=false",
//                "root2","015036");
//        Statement statement = connection.createStatement();
//        System.out.println(connection.isClosed());
        SpringApplication.run(WeixinSellApplication.class, args);
    }

}

